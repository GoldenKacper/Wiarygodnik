import { Box, Button, Container, Divider, Tooltip, Typography } from "@mui/material";
import NavBar from "../layout/NavBar.tsx";
import PersonIcon from '@mui/icons-material/Person';
import theme from "../theme.ts";
import LogoutIcon from '@mui/icons-material/Logout';
import { userPageButton, userPageButtonIcon, userPageButtonText, userPageDivider } from "../Style.tsx";
import SettingsIcon from '@mui/icons-material/Settings';
import useKeycloak from "../features/auth/hooks/useKeycloak.ts";
import { PushNotificationButton } from "../features/notification/components/PushNotificationButton.tsx";

function User() {
    const isOffline = !navigator.onLine;

    const { keycloak, logout } = useKeycloak();

    const handleSettings = () => {
        if (!isOffline) {
            keycloak?.accountManagement();
        }
    };

    const handleLogout = () => {
        if (keycloak) {
            logout(keycloak, window.location.origin);
        }
    }

    return (
        <Container sx={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
            <NavBar menuActive={undefined} setMenuActive={undefined} />
            <Box sx={{ width: "100vw", height: "calc(100vh - 100px)", display: "flex", flexDirection: "column", alignItems: "center" }}>
                <Box sx={{
                    margin: "auto", backgroundColor: theme.palette.background.paper, borderRadius: "15px", p: "15px",
                    display: "flex", flexDirection: "column", alignItems: "center",
                    width: "300px", height: "320px"
                }}>
                    <Box sx={{ display: "flex", flexDirection: "column", alignItems: "center", }}>
                        <PersonIcon sx={{ fontSize: "5rem" }} />
                        <Typography sx={{ fontSize: "2rem", textAlign: "center" }}>{keycloak?.tokenParsed?.name}</Typography>
                    </Box>
                    <Box sx={{ width: "100%", display: "flex", flexDirection: "column", alignItems: "center", marginBottom: 0, marginTop: "auto" }}>
                        <Divider sx={userPageDivider} />
                        <PushNotificationButton />
                        <Tooltip title={isOffline ? "Niedostępne w trybie offline" : ""}>
                            <Button aria-label={"settings"} sx={userPageButton} onClick={handleSettings}>
                                <SettingsIcon sx={userPageButtonIcon} />
                                <Typography sx={userPageButtonText}>Ustawienia</Typography>
                            </Button>
                        </Tooltip>
                        <Divider sx={userPageDivider} />
                        <Button
                            aria-label={"log-out"}
                            sx={{ ...userPageButton, color: theme.palette.text.secondary }}
                            onClick={handleLogout}
                        >
                            <LogoutIcon sx={userPageButtonIcon} />
                            <Typography sx={userPageButtonText}>Wyloguj</Typography>
                        </Button>
                    </Box>
                </Box>
            </Box>
        </Container>
    )
}

export default User
