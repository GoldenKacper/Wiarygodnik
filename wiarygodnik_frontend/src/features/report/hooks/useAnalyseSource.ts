import { useAnalysisApi, type AnalyseResponse } from "../api/analysis.api";

export const useAnalyseSource = () => {
  const { analyseSource } = useAnalysisApi();
  
  const startAnalysis = async (url: string) => {
    const response: AnalyseResponse = await analyseSource({ url });
    return response.requestId;
  };

  return { startAnalysis };
};

