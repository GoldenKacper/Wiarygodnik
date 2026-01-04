import { useEffect, useState } from 'react';
import { useReportApi, type ReportListItemResponse } from '../api/report.api';
import { useOnlineStatus } from '../../../hooks/useOnlineStatus';

export const useReportsList = (reload: boolean | undefined) => {
  const online = useOnlineStatus()

  const { getAllMyReports } = useReportApi();
  
  const [reports, setReports] = useState<ReportListItemResponse[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getAllMyReports()
      .then(setReports)
      .finally(() => setLoading(false));
  }, [reload, online]);

  return { reports, loading };
};

