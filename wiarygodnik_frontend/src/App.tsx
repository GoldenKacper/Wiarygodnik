import { ToastProvider } from './components/toast/ToastProvider.tsx';
import { KeycloakProvider } from './shared/context/KeycloakContext.tsx';
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