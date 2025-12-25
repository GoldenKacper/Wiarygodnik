import { useIsMobile } from "../common/UseIsMobile.tsx";
import { Box, Button, Typography, AppBar, Toolbar } from "@mui/material";
import logo from "/logo_full_350_80.png";
import AccountCircleOutlinedIcon from '@mui/icons-material/AccountCircleOutlined';
import MenuOutlinedIcon from '@mui/icons-material/MenuOutlined';
import theme from "../theme.ts";
import { useLocation, useNavigate } from "react-router-dom";
import useKeycloak from "../hooks/useKeycloak.tsx";

type NavBarProps = {
    menuActive: boolean | undefined;
    setMenuActive: React.Dispatch<React.SetStateAction<boolean>> | undefined;
};

function NavBar(props: NavBarProps) {
    const isMobile = useIsMobile();
    const navigate = useNavigate();
    const location = useLocation();
    const { authenticated } = useKeycloak();

    function handleHome() {
        navigate("/")
    }

    function handleUserClick() {
        navigate("/user");
    }

    function handleMenuClick() {
        if (props.setMenuActive) {
            props.setMenuActive(!props.menuActive);
        }
    }

    return (
        <AppBar position="static" color="transparent" elevation={0}>
            <Toolbar sx={{
                display: "grid",
                gridTemplateColumns: "1fr auto 1fr",
                alignItems: "center",
                px: isMobile ? 0 : 4
            }}>
                <Box sx={{ justifySelf: "start", width: "70px" }}>
                    {!authenticated || location.pathname !== "/raports" ? null :
                        <Button aria-label={"menu-button"} onClick={handleMenuClick}>
                            <MenuOutlinedIcon sx={{ color: theme.palette.primary.main, fontSize: "2rem" }} />
                        </Button>
                    }
                </Box>

                <Box sx={{ justifySelf: "center" }}>
                    <Button aria-label={"home-button"} disabled={location.pathname === "/"} onClick={handleHome}>
                        <img
                            alt="Wiarygodnik"
                            src={logo}
                            style={{ height: isMobile ? "50px" : "80px" }}
                        />
                    </Button>
                </Box>

                <Box sx={{ justifySelf: "end", width: "70px" }}>
                    {authenticated && location.pathname !== "/user" ? (
                        <Button aria-label={"account-button"} onClick={handleUserClick}>
                            <AccountCircleOutlinedIcon sx={{ color: theme.palette.primary.main, fontSize: isMobile ? "2rem" : "3rem" }} />
                        </Button>
                    ) : null}
                </Box>

            </Toolbar>
        </AppBar>
    );
}

export default NavBar
