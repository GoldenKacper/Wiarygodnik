import { Button, IconButton, Box } from "@mui/material";
import DeleteIcon from '@mui/icons-material/Delete';
import { sideMenuButton } from "../../../Style.tsx";
import { useNavigate } from "react-router-dom";
import { useDeleteReport } from "../hooks/useDeleteReport.ts";

interface ReportListButtonProps {
    reportTitle: string;
    requestId: string;
    setRecentlyDeletedReqId: (recentlyDeletedReqId: string | undefined) => void;
    currentPageReqId?: string;
}

export const ReportListButton = ({ reportTitle, requestId, setRecentlyDeletedReqId, currentPageReqId }: ReportListButtonProps) => {
    const navigate = useNavigate();

    const { deleteReport } = useDeleteReport();

    const handleClick = () => navigate(`/reports/${requestId}`);

    const handleDelete = async () => {
        if (!window.confirm("Czy na pewno chcesz usunąć raport?")) return;
        await deleteReport(requestId);
        setRecentlyDeletedReqId(requestId);

        if (currentPageReqId && currentPageReqId === requestId) {
            navigate('/reports')
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