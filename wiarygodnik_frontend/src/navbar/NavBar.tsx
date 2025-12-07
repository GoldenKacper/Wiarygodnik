import {useIsMobile} from "../common/UseIsMobile.tsx";
import {Box, Button} from "@mui/material";
import logo from "/logo_full_350_80.png";
import AccountCircleOutlinedIcon from '@mui/icons-material/AccountCircleOutlined';
import MenuOutlinedIcon from '@mui/icons-material/MenuOutlined';
import theme from "../theme.ts";
import {useNavigate} from "react-router-dom";

type NavBarProps = {
    menuActive: boolean;
    setMenuActive: React.Dispatch<React.SetStateAction<boolean>>;
};

function NavBar(props: NavBarProps) {
    const isMobile = useIsMobile();
    const navigate = useNavigate();

    function handleUserClick() {
        navigate("/user");
    }

    function handleMenuClick() {
        props.setMenuActive(!props.menuActive);
    }

    return (
        <Box
            sx={{
                display: "flex",
                width: "100vw",
                height: "100px",
                alignItems: "center",
                px: isMobile? 0 : 4
            }}
        >
            <Box sx={{ flex: 1, display: "flex", alignItems: "center", width: "70px" }}>
                {(
                    <Button aria-label={"menu-button"} onClick={handleMenuClick}>
                        <MenuOutlinedIcon sx={{ color: theme.palette.primary.main, fontSize: "2rem" }}/>
                    </Button>
                )}
            </Box>

            <Box sx={{ flex: 1, display: "flex", justifyContent: "center" }}>
                <img
                    alt="Wiarygodnik"
                    src={logo}
                    style={{ height: isMobile ? "50px" : "80px" }}
                />
            </Box>

            <Box sx={{ flex: 1, display: "flex", justifyContent: "flex-end", alignItems: "center", width: "70px" }}>
                <Button aria-label={"account-button"} onClick={handleUserClick}>
                    <AccountCircleOutlinedIcon sx={{ color: theme.palette.primary.main, fontSize: isMobile ? "2rem" : "3rem" }}/>
                </Button>
            </Box>
        </Box>
    );
}

export function NavBarUserPage() {
    const isMobile = useIsMobile();
    const navigate = useNavigate();

    function handleHome() {
        navigate("/")
    }

    return (
        <Box
            sx={{
                display: "flex",
                width: "100vw",
                height: "100px",
                alignItems: "center",
                px: 0
            }}
        >
            <Box sx={{ flex: 1, display: "flex", justifyContent: "center" }}>
                <Button aria-label={"home-button"}>
                    <img
                        alt="Wiarygodnik"
                        src={logo}
                        style={{ height: isMobile ? "50px" : "80px" }}
                        onClick={handleHome}
                    />
                </Button>
            </Box>
        </Box>
    )
}

export default NavBar
