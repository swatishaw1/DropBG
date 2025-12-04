## 📁 Frontend Project Structure

DropBGFrontend/
│
├── src/
│   │
│   ├── assets/
│   │   └── assets.js                                  # Static images & icons
│   │
│   ├── Components/
│   │   ├── Header.jsx                                 # Navbar + upload trigger
│   │   ├── Footer.jsx                                 # Footer section
│   │   └── Result.jsx                                 # Before/after preview UI
│   │
│   ├── Context/
│   │   └── AppContext.jsx                             # Global state: credits, images, API calls
│   │
│   ├── Middleware/
│   │   └── UserSyncHandler.jsx                        # Syncs Clerk user → backend
│   │
│   ├── Pages/
│   │   └── Home.jsx                                   # Main upload page UI
│   │
│   ├── App.jsx                                        # Routing + layout
│   ├── main.jsx                                       # App entry point
│   └── index.css                                      # Tailwind base styles
│
├── public/
│   └── vite.svg                                       # Public assets
│
├── package.json
├── tailwind.config.js                                 # Tailwind configuration
├── postcss.config.js                                  # PostCSS config
└── README.md
