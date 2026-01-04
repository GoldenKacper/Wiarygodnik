import { Box, Button, Input, Typography } from "@mui/material";
import theme from "../../../theme.ts";
import SearchOutlinedIcon from '@mui/icons-material/SearchOutlined';
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAnalyseSource } from "../hooks/useAnalyseSource.ts";
import { useOnlineStatus } from "../../../hooks/useOnlineStatus.ts";
import { enqueueSnackbar } from "notistack";

export const ReportSearch = () => {
    const online = useOnlineStatus()
    const navigate = useNavigate();

    const { startAnalysis } = useAnalyseSource();

    const [sourceUrl, setSourceUrl] = useState<string>("");

    const handleSearch = async () => {
        navigator.vibrate?.(200);
        try {
            const requestId = await startAnalysis(sourceUrl);
            navigate(`/reports/${requestId}`);
        } catch (e) {
            if (!online) {
                enqueueSnackbar("Brak połączenia z internetem. Analiza zostanie rozpoczęta automatycznie po przywróceniu połączenia.", { variant: 'success' });
                return;
            }
            return;
        }
    };

    return (
        <>
            <Box sx={{ margin: "auto", display: "flex", flexDirection: "column" }}>
                <Typography sx={{ display: "flex", fontSize: "2rem", fontWeight: "bold", color: theme.palette.background.paper }}>
                    <SearchOutlinedIcon sx={{ alignSelf: "center", marginRight: "15px", fontSize: "3rem" }} />
                    Podaj źródło, żeby sprawdzić jego wiarygodność
                </Typography>
            </Box>
            <Box sx={{ display: "flex", height: "70px", width: "100%", backgroundColor: theme.palette.background.paper, borderRadius: "15px", marginTop: "auto", boxShadow: "0 0 10px #181a20" }}>
                <Input value={sourceUrl} onChange={(e) => setSourceUrl(e.target.value)}
                    placeholder="Podaj źródło" disableUnderline={true}
                    sx={{ fontWeight: "light", fontSize: "1.2rem", padding: "10px", width: "100%" }} />
                <Button aria-label={"search-button"} onClick={handleSearch} sx={{ m: "5px", borderRadius: "10px" }}>
                    <SearchOutlinedIcon sx={{ alignSelf: "center", m: "5px", fontSize: "2rem" }} />
                </Button>
            </Box>
        </>
    );
}