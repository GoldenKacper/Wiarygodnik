import { useEffect, useState } from 'react'
import type Keycloak from 'keycloak-js'
import type { KeycloakProfile } from 'keycloak-js'

export function useKeycloakProfile(keycloak: Keycloak | null, authenticated: boolean) {
  const [profile, setProfile] = useState<KeycloakProfile | null>(null);

  useEffect(() => {
    if (!keycloak || !authenticated) return;

    keycloak
      .loadUserProfile()
      .then(setProfile);

  }, [keycloak, authenticated])

  return profile;
}
