import { useClerk } from "@clerk/clerk-react";
import { useContext } from "react";
import { AppContext } from "../Context/AppContext";

const TryNow = () => {
    const{openSignIn} = useClerk();
    const{removeBg} = useContext(AppContext);

    const openLogin = () => {
        openSignIn({});
    }
    return ( 
        <div className="flex flex-col items-center justify-center bg-white px-4 mt-5">
            <h2 className="text-3xl md:text-4xl font-bold text-gray-900 mb-7 text-center">
                Remove Image Background
            </h2>
            <p className="text-gray-500 mb-8 text-center">
                Get a Transparent Background for Any
            </p>
            <div className="bg-white rounded-2xl shadow-lg p-10 flex flex-col items-center space-y-4">
                <input type="file" id="upload2" hidden accept="image/*" onChange={(e) => removeBg(e.target.files[0])}/>
                <label htmlFor="upload2" 
                className="bg-indigo-600 hover:bg-blue-700 text-white font-semibold py-4 px-6 rounded-full text-lg transform hover:scale-105 cursor-pointer">
                    Upload Image
                </label>
                <p className="text-gray-500 text-sm">
                    or drop a file, paste image or <a href="#" className="text-blue-500 underline transform hover:scale-105 cursor-pointer">URL</a>
                </p>
            </div>
        </div>
     );
}
 
export default TryNow;