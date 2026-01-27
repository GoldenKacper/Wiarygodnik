import { useCallback } from "react";
import { useReportApi } from "../api/report.api";

export const useDeleteReport = () => {
    const { deleteMyReport } = useReportApi();

    const deleteReport = useCallback(async (requestId: string) => {
        await deleteMyReport(requestId);
    }, []);

    return { deleteReport };
};
