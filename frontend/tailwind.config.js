/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,jsx}"],
  theme: {
    screens: {
      "phone": "320px",
      "tablet": "640px",
      "laptop": "1024px",
      "desktop": "1200px",
    },
    extend: {
      colors: {
        "gs-blue": "rgb(26,56,92)",
        "gs-accent-blue": "rgb(24,76,137)",
        "gs-orange": "rgb(244,97,98)",
        "gs-dark-orange": "rgb(220, 87, 88)",
        "black": "#494a4b",
        "neutral-light": "#e6e6e6",
        "neutral": "#f2f2f2",
      },
    },
  },
  plugins: [],
}
