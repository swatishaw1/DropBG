import toast from "react-hot-toast";
import { assets } from "../assets/assets";
import { SignedOut, useClerk } from "@clerk/clerk-react";
import { useContext } from "react";
import { AppContext } from "../Context/AppContext";

const Header = () => {
    const{ openSignIn} = useClerk();
    const{removeBg} = useContext(AppContext);

    const openLogin = () => {
        openSignIn({});
    }
    return (
        <div className="grid grid-cols-1 md:grid-cols-2 gap-8 items-center mb-16">
            {/*Left Side: Video Banner*/}
            <div className="order-2 md:order-1 flex justify-center">
                <div className="shadow-[0_25px_50px_-12px_rgba(0,0,0,0.15)] rounded-3xl overflow-hidden">
                    <video src={assets.video_banner} autoPlay loop muted className="w-full max-w-[400px] h-auto object-cover" />
                </div>
            </div>
            {/*Right Side:text Content*/}
            <div className="order-1 md:order-2">
                <h1 className="text-4xl md:text-5xl font-bold text-gray-900 mb-6 leading-tight">
                    The Fastest <span className="text-indigo-700">background eraser.</span>
                </h1>
                <p className="text-gray-600 mb-8 text-lg leading-relaxed">
                    Transform your photos with our background remover app! Highlight your subject and create a transparent background, so you can place it in a variety of new designs and destinations. Try it now and immense your subject in a compeletely different environment!
                </p>
                <div>
                    <input type="file" accept="image/*" id="upload1" hidden onChange={(e)=>removeBg(e.target.files[0])}/>
                    <label htmlFor="upload1" className="bg-black text-white font-medium px-8 py-4 rounded-full hover:opacity-90 transition-transform hover:scale-105 text-lg cursor-pointer">
                        Upload your image
                    </label>
                </div>
            </div>

        </div>
    );
}

export default Header;