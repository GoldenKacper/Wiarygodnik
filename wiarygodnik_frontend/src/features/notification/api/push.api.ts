import { useHttpClient } from "../../../hooks/useHttpClient";

const API_PUSH_PATH = '/api/push';

export const usePushApi = () => {
  const http = useHttpClient(import.meta.env.VITE_REPORT_GENERATION_SERVICE_API_URL);

  const postSubscribe = async (payload: PushSubscription) => {
    await http.post(`${API_PUSH_PATH}/subscribe`, payload);
  };

  return { postSubscribe };
};