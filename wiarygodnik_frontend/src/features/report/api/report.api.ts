import { useHttpClient } from "../../../hooks/useHttpClient";

export type ReportCreadabilityLevel = "HIGH" | "MEDIUM" | "LOW";

export interface ReportListItemResponse {
  requestId: string;
  title: string;
}

export interface ReportContentResponse {
  requestId: string;
  title: string;
  sourceUrl: string;
  credibilityLevel: ReportCreadabilityLevel;
  content: string;
  similarSourceUrls: string[];
}

export interface ReportStatusResponse {
  status: string;
}

export interface ReportStatusResponse {
  status: string;
}

const API_REPORT_PATH = '/api/report';

export const useReportApi = () => {
  const http = useHttpClient(import.meta.env.VITE_REPORT_GENERATION_SERVICE_API_URL);

  const getAllMyReports = async (): Promise<ReportListItemResponse[]> => {
    const response = await http.get<ReportListItemResponse[]>(`${API_REPORT_PATH}`);
    return response.data;
  };

  const getMyReport = async (requestId: string): Promise<ReportContentResponse> => {
    const response = await http.get<ReportContentResponse>(`${API_REPORT_PATH}/${requestId}`);
    return response.data;
  };

  const getMyReportStatus = async (requestId: string): Promise<ReportStatusResponse> => {
    const response = await http.get<ReportStatusResponse>(`${API_REPORT_PATH}/status/${requestId}`);
    return response.data;
  };

  return { getAllMyReports, getMyReport, getMyReportStatus };
};