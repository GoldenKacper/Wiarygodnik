import React, { createContext, useEffect, useRef, useState } from 'react';
import Keycloak from 'keycloak-js';
import { useOnlineStatus } from '../../../hooks/useOnlineStatus';

const STORAGE_KEYS = {
    token: 'kcToken',
    refreshToken: 'kcRefreshToken',
    tokenParsed: 'kcTokenParsed',
};

const saveTokens = (kc: Keycloak) => {
    if (kc.token) localStorage.setItem(STORAGE_KEYS.token, kc.token);
    if (kc.refreshToken) localStorage.setItem(STORAGE_KEYS.refreshToken, kc.refreshToken);
    if (kc.tokenParsed) localStorage.setItem(STORAGE_KEYS.tokenParsed, JSON.stringify(kc.tokenParsed));
};

const loadStoredTokens = () => ({
    token: localStorage.getItem(STORAGE_KEYS.token),
    refreshToken: localStorage.getItem(STORAGE_KEYS.refreshToken),
    tokenParsed: localStorage.getItem(STORAGE_KEYS.tokenParsed),
});

const createKeycloak = () =>
    new Keycloak({
        url: import.meta.env.VITE_KEYCLOAK_URL,
        realm: import.meta.env.VITE_KEYCLOAK_REALM,
        clientId: import.meta.env.VITE_KEYCLOAK_CLIENT,
    });

const restoreOfflineSession = (keycloak: Keycloak, setAuthenticated: (v: boolean) => void) => {
    const { token, refreshToken, tokenParsed } = loadStoredTokens();

    if (!token || !refreshToken) return;

    keycloak.token = token;
    keycloak.refreshToken = refreshToken;
    keycloak.tokenParsed = tokenParsed ? JSON.parse(tokenParsed) : undefined;
    keycloak.realmAccess = { roles: keycloak.tokenParsed?.realm_access?.roles || [] }

    setAuthenticated(true);
};

const initOnline = async (keycloak: Keycloak, setAuthenticated: (v: boolean) => void) => {
    const authenticated = await keycloak.init({ onLoad: 'check-sso', checkLoginIframe: false, pkceMethod: 'S256' });
    setAuthenticated(authenticated);
    saveTokens(keycloak);
};

const logout = (keycloak: Keycloak, redirectUri: string) => {
    localStorage.removeItem(STORAGE_KEYS.token);
    localStorage.removeItem(STORAGE_KEYS.refreshToken);
    localStorage.removeItem(STORAGE_KEYS.tokenParsed);
    keycloak.logout({ redirectUri: redirectUri })
};

interface KeycloakContextProps {
    keycloak: Keycloak | null;
    authenticated: boolean;
    logout: (keycloak: Keycloak, redirectUri: string) => void;
}

interface KeycloakProviderProps {
    children: React.ReactNode;
}

export const KeycloakContext = createContext<KeycloakContextProps | undefined>(undefined);

export const KeycloakProvider: React.FC<KeycloakProviderProps> = ({ children }) => {
    const online = useOnlineStatus();
    const initialized = useRef(false);

    const [keycloak, setKeycloak] = useState<Keycloak | null>(null);
    const [authenticated, setAuthenticated] = useState(false);

    useEffect(() => {
        if (initialized.current) return;
        initialized.current = true;

        const keycloak = createKeycloak();

        restoreOfflineSession(keycloak, setAuthenticated);

        if (navigator.onLine) {
            initOnline(keycloak, setAuthenticated)
                .catch(() => setAuthenticated(false));
        }

        setKeycloak(keycloak);
    }, []);

    useEffect(() => {
        if (!keycloak || !online) return;

        console.log(online);

        initOnline(keycloak, setAuthenticated)
            .catch(() => setAuthenticated(false));
    }, [online]);

    return (
        <KeycloakContext.Provider value={{ keycloak, authenticated, logout }}>
            {children}
        </KeycloakContext.Provider>
    );
};
