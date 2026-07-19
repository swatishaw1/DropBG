import { useAuth, useClerk, useUser } from "@clerk/clerk-react";
import axios from "axios";
import { createContext, useState } from "react";
import toast from "react-hot-toast";
import { useNavigate } from "react-router-dom";

export const AppContext = createContext();//To use contextValue as global variable

const AppContextProvider = (props) => {
    const backendUrl = import.meta.env.VITE_BACKEND_URL;
    const [credits, setCredits] = useState(false);
    const { getToken } = useAuth();
    const [image, setImage] = useState(null);
    const [resultImage, setResultImage] = useState(null);
    const { isSignedIn } = useUser();
    const { openSignIn } = useClerk();
    const navigate = useNavigate();

    //Here we load User credits
    const loadUserCredits = async () => {
        try {
            const token = await getToken();
            const response = await axios.get(backendUrl + "/users/credits", { headers: { Authorization: `Bearer ${token}` } });
            if (response.data.success) {
                setCredits(response.data.data.credits);//As DropBGResponse has data in it the credits are there
            } else {
                toast.error("Error in Loading Credits in Frontend");
            }
        } catch (error) {
            console.log(error);
            toast.error("Error in Loading Credits in Frontend");
        }
    }

    //Sending image to backend and taking the output to show the frontend
    const removeBg = async (selectedImage) => {
        try {
            if (!isSignedIn) {
                return openSignIn();
            }
            setImage(selectedImage);
            setResultImage(false);
            //navigate to the result
            navigate("/result");
            const token = await getToken();
            const formData = new FormData();
            selectedImage && formData.append("file", selectedImage);
            const { data: base64Image } = await axios.post(backendUrl + "/images/remove-background", formData, { headers: { Authorization: `Bearer ${token}` } });//returns base 64 string 
            setResultImage(`data:image/png;base64, ${base64Image}`);//base 64 string to image data
            setCredits(prev => prev - 1);
        } catch (error) {
            console.error(error);
            toast.error("Error while Removing the backGround image");
        }
    }

    //Global Value Declaration
    const contextValue = {
        backendUrl,
        image, setImage,
        resultImage, setResultImage,
        credits, setCredits,
        loadUserCredits,
        removeBg
    }
    return (
        <AppContext.Provider value={contextValue}>
            {props.children}
        </AppContext.Provider>
    )
}

export default AppContextProvider;