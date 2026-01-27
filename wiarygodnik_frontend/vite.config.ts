import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import mkcert from 'vite-plugin-mkcert'
import { VitePWA } from 'vite-plugin-pwa'

export default defineConfig({
  plugins: [
    react(),
    mkcert(),
    VitePWA({
      devOptions: { enabled: true },
      registerType: 'autoUpdate',
      strategies: 'injectManifest',
      injectManifest: { globPatterns: ['**/*.{js,css,html,ico,png,svg}'] },
      filename: 'sw.ts',
      srcDir: "src/",
      manifest: false,
      includeAssets: ['favicon.ico', 'apple-touch-icon.png']
    })
  ]
})
