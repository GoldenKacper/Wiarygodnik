import { useEffect, useState } from 'react';
import { useAnalysisApi, type AnalysisStatusResponse } from '../api/analysis.api';
import { useReportApi, type ReportStatusResponse } from '../api/report.api';
import { AnalysisStatus } from "../domain/AnalysisStatus.ts";

export const useAnalysisStatus = (requestId: string | undefined) => {
    const { getMyAnalysisStatus } = useAnalysisApi();
    const { getMyReportStatus } = useReportApi();

    const [status, setStatus] = useState<string | undefined>(undefined);

    useEffect(() => {
        if (!requestId) {
            setStatus(undefined);
            return;
        };

        let active = true;

        const poll = async () => {
            let firstCheck = true;
            let analysisCompleted: boolean = false;
            while (active) {
                if (!analysisCompleted) {
                    const res: AnalysisStatusResponse = await getMyAnalysisStatus(requestId);
                    setStatus(res.status);
                    if (res.status === AnalysisStatus.COMPLETED) {
                        analysisCompleted = true;
                    } else if (res.status === AnalysisStatus.FAILED) {
                        break;
                    }
                } else {
                    try {
                        const reportRes: ReportStatusResponse = await getMyReportStatus(requestId);
                        setStatus(reportRes.status);

                        if (reportRes.status === AnalysisStatus.GENERATED) {
                            break;
                        } else if (reportRes.status === AnalysisStatus.FAILED) {
                            break;
                        }
                    } catch (err: any) {
                        if (err.response?.status !== 404) {
                            break;
                        }
                    }
                }

                if (firstCheck) {
                    firstCheck = false;
                } else {
                    await new Promise(resolve => setTimeout(resolve, 2000));
                }
            }
        };

        poll();
        return () => {
            active = false;
        };
    }, [requestId]);

    return status;
};
