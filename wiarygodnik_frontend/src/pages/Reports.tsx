import NavBar from "../layout/NavBar.tsx";
import { Box } from "@mui/material";
import { useEffect, useState } from "react";
import { useIsMobile } from "../hooks/useIsMobile.ts";
import { useNavigate, useParams } from "react-router-dom";
import { useAnalysisStatus } from "../features/report/hooks/useAnalysisStatus.ts";
import { useReport } from "../features/report/hooks/useReport.ts";
import { ReportList } from "../features/report/components/ReportList.tsx";
import { ReportSearch } from "../features/report/components/ReportSearch.tsx";
import { ReportContent } from "../features/report/components/ReportContent.tsx";
import { ReportLoading } from "../features/report/components/ReportLoading.tsx";
import { enqueueSnackbar } from "notistack";
import { AnalysisStatus } from "../features/report/domain/AnalysisStatus.ts";

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
