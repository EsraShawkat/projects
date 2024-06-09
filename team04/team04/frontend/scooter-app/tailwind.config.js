/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./src/**/*.{js,ts,vue,tsx}",
    './components/**/*.{html,js,vue}',
  ],
  theme: {
    extend: {
      colors: {
        'primary': '#2C2C34',
        'secondary': '#C84C09',
        'tertiary': '#85CB33'
      }
    },
  },
  plugins: [],
}


