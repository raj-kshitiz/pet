import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// Proxy all /api requests to the Spring Boot backend so
// we never deal with CORS in development.
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
    },
  },
})
