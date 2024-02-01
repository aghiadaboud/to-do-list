/** @type {import('tailwindcss').Config} */
export default {
    content: ["./src/**/*.{html,js,svelte}"],
    darkMode: 'class',
    theme: {
        screens: {
            sm: '480px',
            md: '768px',
            lg: '976px',
            xl: '1440px',
        },
        extend: {
            colors: {
                whitesmoke: '#f5f5f5',
                darkCharcoal: '#302e2b'
            },
            animation: {
                fadeInRightToLeft: 'fadeInRightToLeft 200ms linear 1ms 1 normal both running'
            },
            keyframes: {
                fadeInRightToLeft: {
                    '0%': {
                        opacity: 0,
                        transform: 'translate(2rem)',
                    },
                    '100%': {
                        opacity: 1,
                        transform: 'translate(0)',
                    },
                }
            },
        },
    },
    plugins: [],
}

