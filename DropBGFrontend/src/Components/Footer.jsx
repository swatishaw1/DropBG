import { assets, FOOTER_CONSTANTS } from "../assets/assets";

const Footer = () => {
    return (
        <footer className="flex item-center justify-between gap-4 px-4 lg:px-44 py-3">
            <a href="#"> <img src={assets.logo} alt="logo" width={32} className="transform hover:scale-105 cursor-pointer"/></a>
            <p className="flex-1 border-1 border-gray-100 max-sm:hidden">
                &copy; {new Date().getFullYear()} | All Rights reserved.
            </p>
            <div className="flex gap-3">
                {FOOTER_CONSTANTS.map((item,index) => (
                    <a href={item.url} key={index} target="_blank" rel="noopener noreferrar">
                        <img src={item.logo} alt="logo" width={32}/>
                    </a>
                ))}
            </div>
        </footer>
    );
}
 
export default Footer;