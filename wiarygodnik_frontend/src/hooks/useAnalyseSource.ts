import { useAnalysisApi, type AnalyseResponse } from "../http/analysis.api";

export const useAnalyseSource = () => {
  const { analyseSource } = useAnalysisApi();
  
  const startAnalysis = async (url: string) => {
    const response: AnalyseResponse = await analyseSource({ url });
    return response.requestId;
  };

  return { startAnalysis };
};

