import { Box, Typography } from "@mui/material";

interface ReportPartProps {
  content: string;
}

export const ReportPart = ({ content }: ReportPartProps) => (
  <Box sx={{ backgroundColor: "background.paper", borderRadius: 3, p: 2 }}>
    <Typography variant="h5" fontWeight="bold">
      Raport z analizy
    </Typography>

    <Typography sx={{ mt: 1 }}>
      {content}
    </Typography>
  </Box>
);
