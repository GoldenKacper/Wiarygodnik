import { useEffect, useState } from 'react';
import { useReportApi, type ReportContentResponse } from '../api/report.api';
import { AnalysisStatus } from "../domain/AnalysisStatus.ts";

export const useReport = (requestId: string | undefined, status: string | undefined) => {
  const { getMyReport } = useReportApi();
  
  const [report, setReport] = useState<ReportContentResponse | undefined>(undefined);

  useEffect(() => {
    if (!requestId || (status !== AnalysisStatus.GENERATED)) {
      setReport(undefined);
      return;
    }

    getMyReport(requestId).then(setReport);
  }, [requestId, status]);

  return report;
};
