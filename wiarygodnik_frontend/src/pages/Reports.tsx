import NavBar from "../navbar/NavBar.tsx";
import { Box } from "@mui/material";
import { useEffect, useState } from "react";
import { useIsMobile } from "../common/UseIsMobile.tsx";
import { useNavigate, useParams } from "react-router-dom";
import { useAnalysisStatus } from "../hooks/useAnalysisStatus.ts";
import { useReport } from "../hooks/useReport.ts";
import { ReportList } from "../components/reports/ReportList.tsx";
import { ReportSearch } from "../components/reports/ReportSearch.tsx";
import { ReportContent } from "../components/reports/ReportContent.tsx";
import { ReportLoading } from "../components/reports/ReportLoading.tsx";
import { enqueueSnackbar } from "notistack";
import { AnalysisStatus } from "../components/reports/domain/AnalysisStatus.ts";

function Reports() {
    const isMobile = useIsMobile();
    const navigate = useNavigate();

    const { requestId } = useParams();
    const status = useAnalysisStatus(requestId);
    const report = useReport(requestId, status);

    const [menuActive, setMenuActive] = useState<boolean>(!isMobile);

    useEffect(() => {
        if (status === AnalysisStatus.FAILED) {
            enqueueSnackbar('Analiza nie powiodła się. Spróbuj ponownie.', { variant: 'error' });
            navigate('/reports');
        }
    }, [status]);

    return (
        <>
            <NavBar menuActive={menuActive} setMenuActive={setMenuActive} />
            <Box sx={{ display: "flex", width: "100vw" }}>
                {menuActive && <ReportList />}
                <Box sx={{ display: "flex", flexDirection: "column", width: isMobile ? "100%" : "80%", height: "calc(100vh - 100px)", padding: "10px", margin: "auto" }}>
                    {report ? <ReportContent report={report} /> : status ? <ReportLoading loadingStatus={status} /> : <ReportSearch />}
                </Box>
            </Box>
        </>
    )
}

export default Reports;
