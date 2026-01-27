import { Box, Chip, Typography } from "@mui/material";
import type { ContentSummarization } from "../api/analysis.api";

interface ContentSummaryPartProps {
  contentSummary: ContentSummarization;
}

export const ContentSummaryPart = ({ contentSummary }: ContentSummaryPartProps) => (
  <Box sx={{ backgroundColor: "background.paper", borderRadius: 3, p: 2 }}>
    <Typography variant="h6" fontWeight="bold">
      Podsumowanie treści
    </Typography>

    <Typography sx={{ mt: 1 }}>
      {contentSummary.description}
    </Typography>

    <Box sx={{ mt: 2, display: "flex", gap: 1, flexWrap: "wrap" }}>
      {contentSummary.keywords.map((k, i) => (
        <Chip key={i} label={k} size="small" />
      ))}
    </Box>
  </Box>
);
