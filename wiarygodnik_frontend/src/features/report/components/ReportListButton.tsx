import { Button, IconButton, Box } from "@mui/material";
import DeleteIcon from '@mui/icons-material/Delete';
import { sideMenuButton } from "../../../Style.tsx";
import { useNavigate } from "react-router-dom";
import { useDeleteReport } from "../hooks/useDeleteReport.ts";
import { useOnlineStatus } from "../../../hooks/useOnlineStatus.ts";
import { enqueueSnackbar } from "notistack";

interface ReportListButtonProps {
    reportTitle: string;
    requestId: string;
    setRecentlyDeletedReqId: (recentlyDeletedReqId: string | undefined) => void;
    currentPageReqId?: string;
}

export const ReportListButton = ({ reportTitle, requestId, setRecentlyDeletedReqId, currentPageReqId }: ReportListButtonProps) => {
    const online = useOnlineStatus();
    const navigate = useNavigate();

    const { deleteReport } = useDeleteReport();

    const handleClick = () => navigate(`/reports/${requestId}`);

    const handleDelete = async () => {
        if (!window.confirm("Czy na pewno chcesz usunąć raport?")) return;
        try {
            await deleteReport(requestId);
            setRecentlyDeletedReqId(requestId);

            if (currentPageReqId && currentPageReqId === requestId) {
                navigate('/reports')
            }
        } catch (e) {
            if (!online) {
                enqueueSnackbar("Raport zostanie usunięty automatycznie po przywróceniu połączenia.", { variant: 'success' });
                return;
            }
            return;
        }
    }

    return (
        <Box
            sx={{
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'space-between',
                width: '100%',
                padding: 0.5,
            }}
        >
            <Button
                aria-label={reportTitle}
                sx={sideMenuButton}
                onClick={handleClick}
            >
                {reportTitle}
            </Button>

            <IconButton
                aria-label="Usuń raport"
                color="primary"
                sx={sideMenuButton}
                onClick={handleDelete}
            >
                <DeleteIcon fontSize="small" />
            </IconButton>
        </Box>
    );
}