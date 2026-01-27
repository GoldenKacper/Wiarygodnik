import { Button } from '@mui/material';
import ClearIcon from '@mui/icons-material/Clear';
import { closeSnackbar, SnackbarProvider } from 'notistack';

interface ToastProviderProps {
  children: React.ReactNode;
}

export const ToastProvider = ({ children }: ToastProviderProps) => (
  <SnackbarProvider
    maxSnack={3}
    hideIconVariant={true}
    autoHideDuration={null}
    anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
    action={(snackbarId) => (
      <Button onClick={() => closeSnackbar(snackbarId)} sx={{ width: "fit-content" }}>
        <ClearIcon />
      </Button>
    )}
  >
    {children}
  </SnackbarProvider>
);
