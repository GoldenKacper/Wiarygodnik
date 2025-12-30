import Button from "@mui/material/Button";
import { sideMenuButton } from "../../../Style.tsx";
import { useNavigate } from "react-router-dom";

interface ReportListButtonProps {
    reportTitle: string;
    requestId: string;
}

export const ReportListButton = ({ reportTitle, requestId }: ReportListButtonProps) => {
    const navigate = useNavigate();

    const handleClick = () => {
        navigate(`/reports/${requestId}`);
    }

    return (
        <Button
            aria-label={reportTitle}
            sx={sideMenuButton}
            onClick={handleClick}
        >
            {reportTitle}
        </Button>
    );
}