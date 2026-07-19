import { Route, Routes } from "react-router-dom";
import Footer from "./Components/Footer";
import Menubar from "./Components/Menubar";
import Home from "./pages/Home";
import { Toaster } from "react-hot-toast";
import UserSyncHandler from "./Components/UserSyncHandler";
import Result from "./pages/Result";
import { RedirectToSignIn, SignedIn, SignedOut } from "@clerk/clerk-react";

const App = () => {
    return (
        <div>
            <UserSyncHandler />
            <Menubar />
            <Toaster />
            <Routes>
                <Route path="/" element={<Home />}></Route>
                <Route path="/result" element={
                    <>
                        <SignedIn>
                            <Result />
                        </SignedIn>
                        <SignedOut>
                            <RedirectToSignIn />
                        </SignedOut>
                    </>
                }></Route>
            </Routes>
            <Footer />
        </div>
    )
}

export default App;