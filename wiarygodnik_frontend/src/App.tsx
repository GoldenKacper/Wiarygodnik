import { KeycloakProvider } from './context/KeycloakContext.tsx';
import AppRouter from './routing/AppRouter.tsx';

function App() {
    return (
        <KeycloakProvider>
            <AppRouter />
        </KeycloakProvider>
    );
}

export default App