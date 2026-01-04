import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import { VitePWA } from 'vite-plugin-pwa'

export default defineConfig({
  plugins: [
    react(),
    VitePWA({
      registerType: 'autoUpdate',
      devOptions: {
        enabled: true
      },
      manifest: false,
      includeAssets: ['favicon.ico', 'apple-touch-icon.png'],
      workbox: {
        cleanupOutdatedCaches: true,
        runtimeCaching: [
          {
            urlPattern: ({ url }) => url.pathname.startsWith('/api'),
            handler: 'NetworkFirst' as const,
            options: {
              cacheName: 'api-cache',
              networkTimeoutSeconds: 3,
              expiration: {
                maxEntries: 50,
                maxAgeSeconds: 60 * 5 // 5 minutes
              },
              cacheableResponse: {
                statuses: [0, 200]
              }
            }
          },
          {
            urlPattern: ({ url }) => url.pathname.startsWith('/api'),
            handler: 'NetworkOnly',
            method: 'POST',
            options: {
              backgroundSync: {
                name: 'backgroundSyncQueue',
                options: {
                  maxRetentionTime: 24 * 60 // 24 hours
                }
              }
            }
          }
        ]
      }
    })
  ]
})
