import { createTheme } from "@mui/material/styles";

const theme = createTheme({
    palette: {
        primary: {
            main: "#ffffff",
            light: "#ffffff",
            dark: "#000000",
        },
        secondary: {
            main: "#ff4081",
        },
        background: {
            default: "#292d37",
            paper: "#181a20"
        },
        text: {
            primary: "#ffffff",
            secondary: "#bababa",
        }
    },

    typography: {
        fontFamily: `"Kanit", sans-serif`,
        h1: { fontWeight: 700 },
        h2: { fontWeight: 600 },
        body1: { fontSize: "1rem" },
    },
});

export default theme;
