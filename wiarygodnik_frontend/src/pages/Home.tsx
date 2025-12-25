import { Box, Button, Container, Typography } from "@mui/material";
import { Link } from "react-router-dom";
import NavBar from "../navbar/NavBar";
import SearchOutlinedIcon from '@mui/icons-material/SearchOutlined';
import { useIsMobile } from "../common/UseIsMobile";
import theme from "../theme";

export const Home = () => {
    const isMobile = useIsMobile();

    return (
        <Container maxWidth="lg" sx={{ height: "100vh" }}>
            <NavBar menuActive={undefined} setMenuActive={undefined} />
            <Box sx={{ display: "flex", alignItems: "center", height: "50vh" }}>
                <Box sx={{ display: "flex", flexDirection: "column", alignItems: "start" }}>
                    <Typography sx={{ fontSize: isMobile ? "1.5rem" : "2.5rem", textAlign: "start" }}>
                        Automatyczna weryfikacja źródeł informacji.
                        <br />
                        Sprawdź, czy treść, którą czytasz lub udostępniasz, jest wiarygodna.
                    </Typography>
                    <Button
                        component={Link}
                        to="/raports"
                        variant="contained"
                        size="large"
                        sx={{
                            width: "fit-content",
                            mt: 4,
                            "&:hover": {
                                background: "gray"
                            }
                        }}
                    >
                        Zacznij weryfikację
                    </Button>
                    <Box sx={{ position: "relative" }}>
                        <SearchOutlinedIcon sx={{
                            fontSize: isMobile ? "40rem" : "55rem",
                            color: theme.palette.background.paper,
                            position: "absolute",
                            bottom: isMobile ? "-23rem" : "-30rem",
                            left: isMobile ? "-9.5rem" : "-2.5rem",
                            zIndex: -1,
                        }} />
                    </Box>
                </Box>
            </Box>
        </Container>
    );
};
