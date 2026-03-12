/** @type {import('tailwindcss').Config} */
const defaultTheme = require("tailwindcss/defaultTheme");

module.exports = {
  content: ["./index.html", "./src/**/*.{js,jsx,ts,tsx}"],
  darkMode: "class",
  theme: {
    extend: {
      colors: {
        brand: {
          primary: "#0D3B66", // Deep Corporate Blue
          secondary: "#1171BA", // Active UI Blue
          accent: "#FF8F00", // High-Attention Orange (for Quote forms)
          success: "#00C853", // Confirmation Green
        },
        neutral: {
          dark: "#1A202C", // Professional Grey-Black for text
          surface: "#F8FAFC", // Soft Grey background
          border: "#E2E8F0", // Subtle separators
        },
      },
      // fontFamily: {
      //   jost: ["Jost", ...defaultTheme.fontFamily.sans],
      //   sans: ["Jost", "Inter", "Roboto", ...defaultTheme.fontFamily.sans],
      //   display: ["Jost", "sans-serif"],
      // },
      boxShadow: {
        professional:
          "0 4px 6px -1px rgba(0, 0, 0, 0.05), 0 2px 4px -1px rgba(0, 0, 0, 0.03)",
      },
    },
  },
  plugins: [],
};
