import { Box, Link, Typography } from "@mui/material";
import { useTheme } from "@mui/material/styles";
import useMediaQuery from "@mui/material/useMediaQuery";
import LightbulbIcon from '@mui/icons-material/Lightbulb';
import AutoAwesomeIcon from '@mui/icons-material/AutoAwesome';
import CircleIcon from '@mui/icons-material/Circle';
import { getDomain } from "../../../lib/domainExtractor.ts";
import { sourceDot, sourceLink } from "../../../Style.tsx";
import type { ReportContentResponse } from "../api/report.api.ts";
import { ReportCredability } from "./ReportCredability.tsx";

interface ReportContentProps {
    report?: ReportContentResponse;
}

export const ReportContent = ({ report }: ReportContentProps) => {
    const theme = useTheme();
    const isMobile = useMediaQuery(theme.breakpoints.down("sm"));
    
    if (!report) {
        return <></>;
    }

    const analyzedSource = report.sourceUrl;
    const similarSourceUrls = report.similarSourceUrls;
    const reportTitle = report.title;
    const reportContent = report.content;
    const reportCreadabilityLevel = report.credibilityLevel;

    return (
            <Box sx={{ display: "flex", flexDirection: isMobile ? "column" : "row", flex: 1, paddingBottom: "10px", gap: isMobile ? 0 : 4 }}>
                <Box sx={{ display: "flex", maxHeight: isMobile ? "calc(50vh - 100px)" : "calc(100vh - 210px)", flexDirection: "column", flex: 1, gap: 2, overflowY: "auto", overflowX: "hidden" }}>
                    <Box sx={{ backgroundColor: theme.palette.background.paper, borderRadius: "15px", padding: "10px" }}>
                        <ReportCredability creadability={reportCreadabilityLevel} />
                    </Box>
                    <Box sx={{ backgroundColor: theme.palette.background.paper, borderRadius: "15px", padding: "10px" }}>
                        <Typography sx={{ fontWeight: "bold", fontSize: "1.8rem" }}>{reportTitle}</Typography>
                    </Box>
                    <Box sx={{ backgroundColor: theme.palette.background.paper, borderRadius: "15px", padding: "10px" }}>
                        <Typography sx={{ display: "flex", fontWeight: "bold", fontSize: "1.2rem" }}>
                            <LightbulbIcon sx={{ alignSelf: "center", marginRight: "10px" }} />
                            Analizowane źródło
                        </Typography>
                        <Typography sx={{ display: "flex", alignItems: "flex-start", alignSelf: "center", marginTop: "5px", fontWeight: "light" }}>
                            <CircleIcon sx={sourceDot} />
                            <Link target="_blank" href={analyzedSource} sx={sourceLink}>
                                {getDomain(analyzedSource)}
                            </Link>
                        </Typography>
                    </Box>
                    <Box sx={{ backgroundColor: theme.palette.background.paper, borderRadius: "15px", padding: "10px" }}>
                        <Typography sx={{ display: "flex", fontWeight: "bold", fontSize: "1.2rem" }}>
                            <AutoAwesomeIcon sx={{ alignSelf: "center", marginRight: "10px" }} />
                            Znalezione źródła
                        </Typography>
                        {
                            similarSourceUrls.map((source, index) => (
                                <Typography key={index} sx={{ display: "flex", alignItems: "flex-start", marginTop: "15px", fontWeight: "light" }}>
                                    <CircleIcon sx={sourceDot} />
                                    <Link target="_blank" href={source} sx={sourceLink}>
                                        {getDomain(source)}
                                    </Link>
                                </Typography>
                            ))
                        }
                    </Box>
                </Box>
                <Box sx={{ display: "flex", flexDirection: "column", maxHeight: isMobile ? "calc(50vh - 100px)" : "calc(100vh - 210px)", flex: 2 }}>
                    <Typography sx={{ fontWeight: "bold", fontSize: "2rem" }}>Raport</Typography>
                    <Box sx={{ display: "flex", height: "calc(100vh - 210px)", overflowY: "auto", textAlign: "justify", textJustify: "inter-word" }}>
                        {reportContent}
                    </Box>
                </Box>
            </Box>
    );
}