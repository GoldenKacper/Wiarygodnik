import { Box, Button, Divider, Typography } from "@mui/material";
import NavBar from "../navbar/NavBar.tsx";
import PersonIcon from '@mui/icons-material/Person';
import theme from "../theme.ts";
import LogoutIcon from '@mui/icons-material/Logout';
import { userPageButton, userPageButtonIcon, userPageButtonText, userPageDivider } from "../Style.tsx";
import InfoIcon from '@mui/icons-material/Info';
import SettingsIcon from '@mui/icons-material/Settings';
import useKeycloak from "../hooks/useKeycloak.tsx";

function User() {
    const { keycloak } = useKeycloak();

    const handleLogout = () => {
        keycloak?.logout();
    };

    return (
        <Box sx={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
            <NavBar menuActive={undefined} setMenuActive={undefined} />
            <Box sx={{ width: "100vw", height: "calc(100vh - 100px)", display: "flex", flexDirection: "column", alignItems: "center" }}>
                <Box sx={{
                    margin: "auto", backgroundColor: theme.palette.background.paper, borderRadius: "15px", p: "15px",
                    display: "flex", flexDirection: "column", alignItems: "center",
                    width: "300px", height: "320px"
                }}>
                    <Box sx={{ display: "flex", flexDirection: "column", alignItems: "center", }}>
                        <PersonIcon sx={{ fontSize: "5rem" }} />
                        <Typography sx={{ fontSize: "2rem", textAlign: "center" }}>{keycloak?.idTokenParsed?.name}!</Typography>
                    </Box>
                    <Box sx={{ width: "100%", display: "flex", flexDirection: "column", alignItems: "center", marginBottom: 0, marginTop: "auto" }}>
                        <Divider sx={userPageDivider} />
                        <Button aria-label={"settings"} sx={userPageButton}>
                            <SettingsIcon sx={userPageButtonIcon} />
                            <Typography sx={userPageButtonText}>Ustawienia</Typography>
                        </Button>
                        <Button aria-label={"info"} sx={userPageButton}>
                            <InfoIcon sx={userPageButtonIcon} />
                            <Typography sx={userPageButtonText}>Informacje</Typography>
                        </Button>
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
        </Box>
    )
}

export default User
