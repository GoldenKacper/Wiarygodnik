import { useHttpClient } from '../../../hooks/useHttpClient';
import { SentimentType } from '../domain/SentimentType';

export interface AnalyseRequest {
  url: string;
}

export interface AnalyseResponse {
  requestId: string;
}

export interface AnalysisStatusResponse {
  status: string;
}

export interface ContentSummarization {
  description: string;
  keywords: string[];
}

export interface SentimentQuote {
  quote: string;
  explanation: string;
}

export interface SentimentGroup {
  sentiment: typeof SentimentType;
  quotes: SentimentQuote[];
}

export interface SentimentAnalysis {
  summary: string;
  examples: SentimentGroup[];
}

export interface ContentAnalysis {
  summarization: ContentSummarization;
  sentiment: SentimentAnalysis;
}

export interface SourceFacts {
  url: string;
  facts: string[];
}

export interface ContentComparison {
  description: string;
  sourcesFacts: SourceFacts[];
}

export interface AnalysisData {
  requestId: string;
  sourceUrl: string;
  contentAnalysis: ContentAnalysis;
  contentComparison: ContentComparison;
}

const API_ANALYSIS_PATH = '/api/analysis';

export const useAnalysisApi = () => {
  const http = useHttpClient(import.meta.env.VITE_CONTENT_ANALYSIS_SERVICE_API_URL);

  const analyseSource = async (payload: AnalyseRequest): Promise<AnalyseResponse> => {
    const response = await http.post<AnalyseResponse>(`${API_ANALYSIS_PATH}/process`, payload);
    return response.data;
  };

  const getMyAnalysisStatus = async (requestId: string): Promise<AnalysisStatusResponse> => {
    const response = await http.get<AnalysisStatusResponse>(`${API_ANALYSIS_PATH}/${requestId}/status`);
    return response.data;
  };

  const getMyAnalysis = async (requestId: string): Promise<AnalysisData> => {
    const response = await http.get<AnalysisData>(`${API_ANALYSIS_PATH}/${requestId}`);
    return response.data;
  }

  return { analyseSource, getMyAnalysisStatus, getMyAnalysis };
};
