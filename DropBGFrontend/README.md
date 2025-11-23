# DropBG Frontend

The DropBG frontend is a React-based application built to deliver an efficient and seamless image background-removal experience. It provides a responsive interface, user authentication, real-time previews, and smooth interaction flows. The UI is designed for clarity and performance, with modular components and optimized asset handling. This frontend is fully prepared for integration with the Spring Boot backend that handles image processing, credits, and upcoming payment workflows.

## Features

* Fully responsive UI built with React and TailwindCSS.
* Before/after comparison slider for processed images.
* Clerk authentication for secure Sign-In, Sign-Up, and user session handling.
* Toast notifications, clean routing structure, and reusable components.
* Optimized for API integration with the backend.

## Folder Structure

```
src/
 ├── Components/
 │    ├── BgRemovalSteps.jsx
 │    ├── BgSlider.jsx
 │    ├── Footer.jsx
 │    ├── Header.jsx
 │    ├── Menubar.jsx
 │    ├── Pricing.jsx
 │    ├── Testimonials.jsx
 │    └── TryNow.jsx
 │
 ├── assets/
 │    ├── assets.jsx
 │    ├── dollar.png
 │    ├── home-page-banner.mp4
 │    ├── logo.png
 │    ├── people-org.png
 │    ├── people.png
 │    └── slide_icon.svg
 │
 ├── pages/
 │    └── Home.jsx
 │
 ├── App.jsx
 ├── App.css
 ├── index.css
 └── main.jsx
```

## Dependencies

```
npm install lucide-react
npm install react
npm install react-dom
npm install react-hot-toast
npm install react-router-dom
npm install tailwindcss
```

If any conflict appears:
```
npm install lucide-react --force
npm install react --force
npm install react-dom --force
npm install react-hot-toast --force
npm install react-router-dom --force
npm install tailwindcss --force
```


## Environment Variables

Create a `.env` file in the project root:

```
VITE_CLERK_PUBLISHABLE_KEY=your_clerk_key
VITE_BACKEND_URL=http://localhost:8080
```

## Run the Project

```
npm install
npm run dev
```

## Technologies Used

* React (Vite)
* TailwindCSS
* Clerk Authentication
* React Router
* React Hot Toast

## Work Done in This Frontend

* Designed and built a complete UI from scratch using modern React patterns.
* Implemented secure client-side authentication with Clerk, including session handling.
* Integrated routing, dynamic components, and responsive layouts.
* Built a reusable UI system including pricing, testimonials, menu bar, and preview components.
* Developed the image before/after slider for processed results.
* Prepared the frontend for backend APIs, credit usage, and upcoming payment integration.

