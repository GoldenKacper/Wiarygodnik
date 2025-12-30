import Button from "@mui/material/Button";
import { sideMenuButton } from "../../Style.tsx";
import { useNavigate } from "react-router-dom";

export const NewReportButton = () => {
    const navigate = useNavigate();

    const handleClick = () => {
        navigate(`/reports`);
    }

    return (
        <Button
            aria-label="new-report-button"
            sx={{...sideMenuButton, marginY: ".5vh"}}
            onClick={handleClick}
        >
            Nowy raport
        </Button>
    );
}