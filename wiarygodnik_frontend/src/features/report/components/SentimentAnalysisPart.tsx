import {
  Accordion,
  AccordionSummary,
  AccordionDetails,
  Typography,
  Box
} from "@mui/material";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import type { SentimentAnalysis } from "../api/analysis.api";
import { mapSentimentType } from "../lib/sentimentTypeMapper";

interface SentimentAnalysisPartProps {
  sentimentAnalysis: SentimentAnalysis;
}

export const SentimentAnalysisPart = ({ sentimentAnalysis }: SentimentAnalysisPartProps) => (
  <Box sx={{ backgroundColor: "background.paper", borderRadius: 3, p: 2 }}>
    <Typography variant="h6" fontWeight="bold">
      Analiza sentymentu
    </Typography>

    <Typography sx={{ mt: 1 }}>{sentimentAnalysis.summary}</Typography>

    {sentimentAnalysis.examples.map((group, i) => (
      <Accordion key={i} sx={{ mt: 1 }}>
        <AccordionSummary expandIcon={<ExpandMoreIcon />}>
          <Typography fontWeight="bold">
            {mapSentimentType(String(group.sentiment))}
          </Typography>
        </AccordionSummary>

        <AccordionDetails>
          {group.quotes.map((q, j) => (
            <Box key={j} sx={{ mb: 2 }}>
              <Typography fontStyle="italic">
                „{q.quote}”
              </Typography>
              <Typography variant="body2" color="text.secondary">
                {q.explanation}
              </Typography>
            </Box>
          ))}
        </AccordionDetails>
      </Accordion>
    ))}
  </Box>
);
