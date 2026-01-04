import React from "react";
import { Outlet } from "react-router-dom";
import useKeycloak from "../features/auth/hooks/useKeycloak";
import AccessDenied from "../pages/AccessDenied";
import Loading from "../pages/Loading";
import ConnectionRequired from "../pages/ConnectionRequired";
import { useOnlineStatus } from "../hooks/useOnlineStatus";

interface ProtectedRouteProps {
    roles: string[] | undefined;
}

const ProtectedRoutes: React.FC<ProtectedRouteProps> = ({ roles }) => {
    const online = useOnlineStatus()
    const { keycloak, authenticated } = useKeycloak();

    console.log("online: " + online + "authenticated: " + authenticated);

    if (!authenticated && !online) {
        return <ConnectionRequired />
    } else if (!keycloak) {
        return <Loading />;
    } else if (!authenticated) {
        keycloak.login();
        return null;
    } else if (roles && !roles.some(role => keycloak.hasRealmRole(role))) {
        return <AccessDenied />;
    }

    return <Outlet />;
};

export default ProtectedRoutes;
