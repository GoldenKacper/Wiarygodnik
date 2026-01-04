import ReactDOM from 'react-dom/client'
import App from './App'
import { CssBaseline, ThemeProvider } from "@mui/material";
import theme from "./theme.ts";
import "./index.css"
import { StrictMode } from 'react';
import { registerSW } from 'virtual:pwa-register'

registerSW({ immediate: true })

ReactDOM.createRoot(document.getElementById('root')!)
    .render(
        <StrictMode>
            <ThemeProvider theme={theme}>
                <CssBaseline />
                <App />
            </ThemeProvider>
        </StrictMode>
    );
