import { useHttpClient } from '../shared/hooks/useHttpClient';

export interface AnalyseRequest {
  url: string;
}

export interface AnalyseResponse {
  requestId: string;
}

export interface AnalysisStatusResponse {
  status: string;
}

const API_ANALYSIS_PATH = '/api/analysis';

export const useAnalysisApi = () => {
  const http = useHttpClient(import.meta.env.VITE_CONTENT_ANALYSIS_SERVICE_API_URL);

  const analyseSource = async (payload: AnalyseRequest): Promise<AnalyseResponse> => {
    const response = await http.post<AnalyseResponse>(`${API_ANALYSIS_PATH}/process`, payload);
    return response.data;
  };

  const getAnalysisStatus = async (requestId: string): Promise<AnalysisStatusResponse> => {
    const response = await http.get<AnalysisStatusResponse>(`${API_ANALYSIS_PATH}/status/${requestId}`);
    return response.data;
  };

  return { analyseSource, getAnalysisStatus };
};
