import { useMemo } from 'react';
import axios, { HttpStatusCode, type AxiosInstance } from 'axios';
import { enqueueSnackbar } from 'notistack';
import useKeycloak from '../features/auth/hooks/useKeycloak';

export const useHttpClient = (baseURL: string): AxiosInstance => {
  const { keycloak, authenticated } = useKeycloak();

  return useMemo(() => {
    const instance = axios.create({
      baseURL,
      withCredentials: true,
    });

    instance.interceptors.request.use((config) => {
      const token = keycloak?.token;
      if (token) config.headers.Authorization = `Bearer ${token}`;
      return config;
    });

    instance.interceptors.response.use(
      (res) => res,
      async (err) => {
        const status = err.response?.status;

        if (status === HttpStatusCode.Unauthorized) {
          try {
            if (keycloak && await keycloak.updateToken()) {
              return instance(err.config); // retry request
            }
          } catch {
            keycloak?.login();
          }

          return Promise.reject(err);
        }

        enqueueSnackbar(err.message || 'Wystąpił błąd', { variant: 'error' });
        return Promise.reject(err);
      }
    );

    return instance;
  }, [keycloak, authenticated, baseURL]);
};
