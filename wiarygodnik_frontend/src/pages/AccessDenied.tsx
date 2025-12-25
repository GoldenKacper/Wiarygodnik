import { Box, Container, Typography } from "@mui/material";
import NavBar from "../navbar/NavBar";

export default function AccessDenied() {
    return (
        <Container maxWidth="lg" sx={{ height: "100vh" }}>
            <NavBar menuActive={undefined} setMenuActive={undefined} />
            <Box sx={{ display: "flex", flexDirection: "column", alignItems: "center", justifyContent: "center", height: "50vh" }}>
                <Typography variant="h2"
                    sx={{
                        fontSize: {
                            xs: "2rem",
                            sm: "2.5rem",
                            md: "3rem",
                            lg: "3.5rem"
                        },
                    }}>
                    Access Denied
                </Typography>
                <Typography sx={{
                    fontSize: {
                        xs: "1rem",
                        sm: "1.1rem",
                        md: "1.2rem",
                        lg: "1.3rem"
                    },
                }}>
                    You do not have permission to view this page.
                </Typography>
            </Box>
        </Container>
    );
}