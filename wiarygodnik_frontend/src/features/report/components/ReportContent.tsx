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
import { ContentSummaryPart } from "./ContentSummaryPart.tsx";
import { SentimentAnalysisPart } from "./SentimentAnalysisPart.tsx";
import { ContentComparisonPart } from "./ContentComparisonPart.tsx";
import type { AnalysisData } from "../api/analysis.api.ts";
import { ReportPart } from "./ReportPart.tsx";

interface ReportContentProps {
    report?: ReportContentResponse;
    analysis?: AnalysisData;
}

export const ReportContent = ({ report, analysis }: ReportContentProps) => {
    const theme = useTheme();
    const isMobile = useMediaQuery(theme.breakpoints.down("sm"));

    if (!report || !analysis) {
        return <></>;
    }

    const similarSourceUrls = analysis.contentComparison.sourcesFacts.map(sourceFact => sourceFact.url);

    return (
        <Box sx={{ display: "flex", flexDirection: isMobile ? "column" : "row", flex: 1, paddingBottom: "10px", gap: isMobile ? 0 : 4 }}>
            <Box sx={{ display: "flex", maxHeight: isMobile ? "calc(50vh - 100px)" : "calc(100vh - 210px)", flexDirection: "column", flex: 1, gap: 2 }}>
                <Box sx={{ backgroundColor: theme.palette.background.paper, borderRadius: "15px", padding: "10px" }}>
                    <ReportCredability creadability={report.credibilityLevel} />
                </Box>
                <Box sx={{ backgroundColor: theme.palette.background.paper, borderRadius: "15px", padding: "10px" }}>
                    <Typography sx={{ fontWeight: "bold", fontSize: "1.8rem" }}>{report.title}</Typography>
                </Box>
                <Box sx={{ backgroundColor: theme.palette.background.paper, borderRadius: "15px", padding: "10px" }}>
                    <Typography sx={{ display: "flex", fontWeight: "bold", fontSize: "1.2rem" }}>
                        <LightbulbIcon sx={{ alignSelf: "center", marginRight: "10px" }} />
                        Analizowane źródło
                    </Typography>
                    <Typography sx={{ display: "flex", alignItems: "flex-start", alignSelf: "center", marginTop: "5px", fontWeight: "light" }}>
                        <CircleIcon sx={sourceDot} />
                        <Link target="_blank" href={report.sourceUrl} sx={sourceLink}>
                            {getDomain(report.sourceUrl)}
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
            <Box sx={{ display: "flex", flexDirection: "column", flex: 2, gap: 2, mt: 3 }}>
                <ReportPart content={report.content} />
                <ContentSummaryPart contentSummary={analysis.contentAnalysis.summarization} />
                <SentimentAnalysisPart sentimentAnalysis={analysis.contentAnalysis.sentiment} />
                {analysis.contentComparison && <ContentComparisonPart contentComparison={analysis.contentComparison} />}
            </Box>
        </Box>
    );
}