import { ToastProvider } from './layout/ToastProvider.tsx';
import { KeycloakProvider } from './features/auth/context/KeycloakContext.tsx';
import AppRouter from './routing/AppRouter.tsx';
import { useEffect } from 'react';
import { syncBadge } from './features/push/lib/badging-db.ts';

function App() {

    useEffect(() => {
        navigator.serviceWorker.addEventListener('message', (event) => {
            if (event.data?.type === 'SYNC_BADGE') {
                syncBadge();
            }
        });

        syncBadge();
    }, []);

    return (
        <ToastProvider>
            <KeycloakProvider>
                <AppRouter />
            </KeycloakProvider>
        </ToastProvider>
    );
}

export default App