import { Box, CircularProgress, Typography } from "@mui/material";
import theme from "../../theme";

interface ReportLoadingProps {
    loadingStatus?: string;
}

export const ReportLoading = ({ loadingStatus }: ReportLoadingProps) => {
    return (
        <Box sx={{
            display: "flex", flexDirection: "column", minWidth: "250px", minHeight: "250px",
            margin: "auto", p: "15px", alignContent: "center",
            backgroundColor: theme.palette.background.paper, borderRadius: "15px", boxShadow: 10
        }}>
            <CircularProgress size="4.5rem" sx={{ margin: "auto" }} />
            <Typography sx={{ textAlign: "center", fontSize: "1.4rem", fontWeight: "light", color: theme.palette.text.secondary }}>
                {loadingStatus}
            </Typography>
        </Box>
    );
}