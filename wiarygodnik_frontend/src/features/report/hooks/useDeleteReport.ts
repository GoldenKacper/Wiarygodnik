import { useCallback } from "react";
import { useReportApi } from "../api/report.api";

export const useDeleteReport = () => {
    const { deleteMyReport } = useReportApi();

    const deleteReport = useCallback(async (requestId: string) => {
        try {
            await deleteMyReport(requestId);
            return true;
        } catch (err: any) {
            return false;
        }
    }, []);

    return { deleteReport };
};
