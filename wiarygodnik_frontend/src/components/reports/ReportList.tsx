import { Box, CircularProgress, Typography } from "@mui/material";
import ArticleIcon from '@mui/icons-material/Article';
import Divider from "@mui/material/Divider";
import { useTheme } from "@mui/material/styles";
import useMediaQuery from "@mui/material/useMediaQuery";
import { ReportListButton } from "./ReportListButton.tsx";
import { useReportsList } from "../../hooks/useReportList.ts";
import { NewReportButton } from "./NewReportButton.tsx";


export const ReportList = () => {
    const theme = useTheme();
    const isMobile = useMediaQuery(theme.breakpoints.down("sm"));

    const { reports, loading } = useReportsList();

    return (
        <Box sx={{
            width: isMobile ? "100vw" : "280px",
            minHeight: "calc(100vh - 100px)",
            padding: "10px",
            position: isMobile ? "fixed" : "relative",
            top: isMobile ? "100px" : 0,
            left: 0,
            zIndex: 1000
        }}>
            <Box sx={{
                minWidth: "100%",
                minHeight: "100%",
                backgroundColor: theme.palette.background.paper,
                borderRadius: "15px",
                padding: "10px",
                boxShadow: "0 0 10px #181a20"
            }}>
                {loading ?
                    <CircularProgress />
                    :
                    <>
                        <Typography sx={{
                            textAlign: "center",
                            fontWeight: "regular",
                            fontSize: "2rem",
                            display: "flex",
                        }}>
                            <ArticleIcon sx={{ alignSelf: "center", marginRight: "15px", fontSize: "2rem", }} />
                            Raporty
                        </Typography>
                        <NewReportButton />
                        <Divider sx={{ backgroundColor: theme.palette.text.secondary, my: "10px" }} />
                        <Box sx={{
                            display: "flex",
                            height: "calc(100vh - 260px)",
                            flexDirection: "column",
                            overflowY: "auto",
                        }}>
                            {reports.map((report, index) => <ReportListButton reportTitle={report.title} requestId={report.requestId} key={index} />)}
                        </Box>
                    </>
                }
            </Box>
        </Box >
    );
};