import { Route, Routes } from "react-router-dom";
import Footer from "./Components/Footer";
import Menubar from "./Components/Menubar";
import Home from "./pages/Home";
import { Toaster } from "react-hot-toast";

const App = () => {
    return(
        <div>
            <Menubar/>
            <Toaster/>
            <Routes>
                <Route path="/" element={<Home/>}></Route>
            </Routes>
            <Footer/>
        </div>
    )
}

export default App;