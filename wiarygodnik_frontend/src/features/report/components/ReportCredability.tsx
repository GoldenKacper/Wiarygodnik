import type { ReportCreadabilityLevel } from "../api/report.api";
import ErrorIcon from '@mui/icons-material/Error';
import CancelIcon from '@mui/icons-material/Cancel';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import { Typography } from "@mui/material";

interface ReportCredabilityProps {
    creadability: ReportCreadabilityLevel;
}

export const ReportCredability = ({ creadability }: ReportCredabilityProps) => {
    let color = "";
    let content: React.ReactNode;

    const iconStyle = { alignSelf: "center", fontSize: "2rem", marginRight: "5px" };

    switch (creadability) {
        case "HIGH":
            color = "#8dff5d";
            content = <><CheckCircleIcon sx={iconStyle} /> Źródło wiarygodne</>;
            break;
        case "MEDIUM":
            color = "#faff5d";
            content = <><ErrorIcon sx={iconStyle} /> Źródło częściowo wiarygodne</>;
            break;
        case "LOW":
            color = "#ff5d5d";
            content = <><CancelIcon sx={iconStyle} /> Źródło niewiarygodne</>;
            break;
        default:
            color = "gray";
            content = <>Unknown</>;
    }

    const typographyStyle = { display: "flex", fontWeight: "bold", fontSize: "1.6rem", color: color };

    return (
        <Typography sx={typographyStyle}>
            {content}
        </Typography>
    );
}