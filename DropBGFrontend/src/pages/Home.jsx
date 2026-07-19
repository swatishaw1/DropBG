import BgRemovalSteps from "../Components/BgRemovalSteps";
import BgSlider from "../Components/BgSlider";
import Header from "../Components/Header";
import Pricing from "../Components/Pricing";
import Testimonials from "../Components/Testimonials";
import TryNow from "../Components/TryNow";

const Home = () => {
    return ( 
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16 font-['Outfit']">
            {/*Hero Section*/}
            <Header/>
            {/*BackGround Removal Section*/}
            <BgRemovalSteps/>
            {/*Background Removal Slider Section*/}
            <BgSlider/>
            {/*Buy Credits Plan Section*/}
            <Pricing/>
            {/*User Testimonials Section*/}
            <Testimonials/>
            {/*Try Now Component*/}
            <TryNow/>
        </div>
     );
}
 
export default Home;