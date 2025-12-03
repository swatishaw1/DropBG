import { useAuth, useUser } from "@clerk/clerk-react";
import { useContext, useEffect, useState } from "react";
import { AppContext } from "../Context/AppContext";
import axios from "axios";
import toast from "react-hot-toast";

const UserSyncHandler = () => {
    const { isLoaded, isSignedIn, getToken } = useAuth();
    const { user } = useUser();
    const [synced, setSynced] = useState(false);
    const { backendUrl,loadUserCredits } = useContext(AppContext);
    useEffect(() => {
        const saveUser = async () => {
            if (!isLoaded || !isSignedIn || synced) {
                return;
            }
            try {
                const token = await getToken();
                const userData = {
                    clerkId: user.id,
                    email: user.primaryEmailAddress.emailAddress,
                    firstName: user.firstName,//The name of both side firstName needed to be same 
                    lastName: user.lastName,//The name of both side lastName needed to be same 
                    photoUrl: user.imageUrl,
                };
                await axios.post(backendUrl + '/users', userData, { headers: { "Authorization": `Bearer ${token}` } });

                // const response = await axios.post(backendUrl + '/users', userData, { headers: { "Authorization": `Bearer ${token}` } });
                // if (response.data.success === true) {
                //     toast.success("User Successfully Created.");
                // } else {
                //     toast.error("User sync failed. Please try again");
                // }
                setSynced(true);//prevent re-posting
                //TODO: update the user credits 
                await loadUserCredits();
            } catch (error) {
                console.error("User Sync failed.", error);
                toast.error("Unable to create account. Please try again");
            }
        }
        saveUser();
    }, [isLoaded, isSignedIn, getToken, user, synced]);
    return null;
}

export default UserSyncHandler;