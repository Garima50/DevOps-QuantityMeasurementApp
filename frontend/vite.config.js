import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      // The Spring Boot backend has no CORS configuration at all, so a
      // direct browser request from :5173 to :8080 would be blocked
      // outright. Proxying through Vite's dev server means the browser
      // only ever talks to :5173 (same-origin) — Vite forwards the
      // request to :8080 server-to-server, where CORS doesn't apply.
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
    },
  },
})
