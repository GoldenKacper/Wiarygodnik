import { useIsMobile } from "../hooks/useIsMobile.ts";
import { Box, Button, AppBar, Toolbar, Tooltip } from "@mui/material";
import logo from "/logo_full_350_80.png";
import AccountCircleOutlinedIcon from '@mui/icons-material/AccountCircleOutlined';
import ArticleIcon from '@mui/icons-material/Article';
import MenuOutlinedIcon from '@mui/icons-material/MenuOutlined';
import theme from "../theme.ts";
import { useLocation, useNavigate } from "react-router-dom";
import useKeycloak from "../features/auth/hooks/useKeycloak.ts";

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

    function handleReportsClick() {
        navigate("/reports");
    }

    return (
        <AppBar position="static" color="transparent" elevation={0}>
            <Toolbar sx={{
                display: "grid",
                gridTemplateColumns: "1fr auto 1fr",
                alignItems: "center",
                px: isMobile ? 0 : 4
            }}>
                <Box sx={{ justifySelf: "start" }}>
                    {authenticated ?
                        location.pathname.startsWith("/reports") ? (
                            <Tooltip title="Ukryj/Pokaż listę raportów">
                                <Button aria-label={"menu-button"} onClick={handleMenuClick}>
                                    <MenuOutlinedIcon sx={{ color: theme.palette.primary.main, fontSize: "2rem" }} />
                                </Button>
                            </Tooltip>
                        ) : (
                            <Tooltip title="Przejdź do raportów">
                                <Button aria-label={"reports-button"} onClick={handleReportsClick}>
                                    <ArticleIcon sx={{ color: theme.palette.primary.main, fontSize: "2rem" }} />
                                </Button>
                            </Tooltip>
                        )
                        : null}
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

                <Box sx={{ display: "flex", justifySelf: "end" }}>
                    {authenticated && location.pathname !== "/user" ? (
                        <Tooltip title="Przejdź do konta">
                            <Button aria-label={"account-button"} onClick={handleUserClick}>
                                <AccountCircleOutlinedIcon sx={{ color: theme.palette.primary.main, fontSize: isMobile ? "2rem" : "3rem" }} />
                            </Button>
                        </Tooltip>
                    ) : null}
                </Box>

            </Toolbar>
        </AppBar>
    );
}

export default NavBar
