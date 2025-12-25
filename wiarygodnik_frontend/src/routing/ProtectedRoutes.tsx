import React from "react";
import { Outlet } from "react-router-dom";
import useKeycloak from "../hooks/useKeycloak";
import AccessDenied from "../pages/AccessDenied";
import Loading from "../pages/Loading";

interface ProtectedRouteProps {
    roles: string[] | undefined;
}

const ProtectedRoutes: React.FC<ProtectedRouteProps> = ({ roles }) => {
    const { keycloak, authenticated } = useKeycloak();

    if (!keycloak) {
        return <Loading />;
    }

    if (!authenticated) {
        keycloak.login();
        return null;
    }

    if (roles && !roles.some(role => keycloak.hasRealmRole(role))) {
        return <AccessDenied />;
    }

    return <Outlet />;
};

export default ProtectedRoutes;
