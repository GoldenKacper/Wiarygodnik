import { useEffect, useState } from 'react';
import { AnalysisStatus } from "../domain/AnalysisStatus.ts";
import { useAnalysisApi, type AnalysisData  } from '../api/analysis.api.ts';

export const useAnalysis = (requestId: string | undefined, status: string | undefined) => {
  const { getMyAnalysis } = useAnalysisApi();
  
  const [analysis, setAnalysis] = useState<AnalysisData | undefined>(undefined);

  useEffect(() => {
    if (!requestId || (status !== AnalysisStatus.GENERATED)) {
      setAnalysis(undefined);
      return;
    }

    getMyAnalysis(requestId).then(setAnalysis);
  }, [requestId, status]);

  return analysis;
};
