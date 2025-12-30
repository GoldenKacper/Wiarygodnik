import { ToastProvider } from './layout/ToastProvider.tsx';
import { KeycloakProvider } from './features/auth/context/KeycloakContext.tsx';
import AppRouter from './routing/AppRouter.tsx';

function App() {
    return (
        <ToastProvider>
            <KeycloakProvider>
                <AppRouter />
            </KeycloakProvider>
        </ToastProvider>
    );
}

export default App