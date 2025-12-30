import { useEffect, useState } from 'react';
import { useReportApi, type ReportListItemResponse } from '../api/report.api';

export const useReportsList = () => {
  const { getAllMyReports } = useReportApi();
  
  const [reports, setReports] = useState<ReportListItemResponse[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getAllMyReports()
      .then(setReports)
      .finally(() => setLoading(false));
  }, []);

  return { reports, loading };
};

