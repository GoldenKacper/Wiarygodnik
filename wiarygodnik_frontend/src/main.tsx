import ReactDOM from 'react-dom/client'
import App from './App'
import { BrowserRouter } from 'react-router-dom'
import {CssBaseline, ThemeProvider} from "@mui/material";
import theme from "./theme.ts";
import "./index.css"
import * as serviceWorkerRegistration from './serviceWorkerRegistration';

ReactDOM.createRoot(document.getElementById('root')!)
    .render(
        <ThemeProvider theme={theme}>
            <CssBaseline />
            <BrowserRouter>
                <App />
            </BrowserRouter>
        </ThemeProvider>
    );

serviceWorkerRegistration.register();
