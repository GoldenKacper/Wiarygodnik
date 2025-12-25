import ReactDOM from 'react-dom/client'
import App from './App'
import { CssBaseline, ThemeProvider } from "@mui/material";
import theme from "./theme.ts";
import "./index.css"
import * as serviceWorkerRegistration from './serviceWorkerRegistration';
import { StrictMode } from 'react';

ReactDOM.createRoot(document.getElementById('root')!)
    .render(
        <StrictMode>
            <ThemeProvider theme={theme}>
                <CssBaseline />
                <App />
            </ThemeProvider>
        </StrictMode>
    );

serviceWorkerRegistration.register();
