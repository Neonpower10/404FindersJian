import { defineConfig } from 'vite'

export default defineConfig({
  root: 'camping-project',          // relative to frontend folder
  base: './',                        // ensures relative paths in index.html
  build: {
    outDir: '../dist',               // output folder for production build
    emptyOutDir: true
  }
})
