import { Box, Typography, Link } from "@mui/material";
import type { ContentComparison } from "../api/analysis.api";
import { getDomain } from "../lib/domainExtractor";

interface ContentComparisonPartProps {
  contentComparison: ContentComparison;
}

export const ContentComparisonPart = ({ contentComparison }: ContentComparisonPartProps) => (
  <Box sx={{ backgroundColor: "background.paper", borderRadius: 3, p: 2 }}>
    <Typography variant="h6" fontWeight="bold">
      Porównanie z innymi źródłami
    </Typography>

    <Typography sx={{ mt: 1 }}>{contentComparison.description}</Typography>

    {contentComparison.sourcesFacts.map((src, i) => (
      <Box key={i} sx={{ mt: 2 }}>
        <Link href={src.url} target="_blank">
          {getDomain(src.url)}
        </Link>

        <ul>
          {src.facts.map((fact, j) => (
            <li key={j}>
              <Typography variant="body2">{fact}</Typography>
            </li>
          ))}
        </ul>
      </Box>
    ))}
  </Box>
);
