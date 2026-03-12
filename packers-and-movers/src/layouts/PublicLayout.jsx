import { Outlet } from "react-router";
import Navbar from "../components/layout/Navbar";
import Footer from "../components/common/Footer";
import { FaWhatsapp } from "react-icons/fa";


function PublicLayout() {
  return (
    <div>
      <Navbar />
      <Outlet />
      <Footer />
      <a
        href="https://wa.me/8989682154?text=Hello!%20I%20want%20to%20know%20more"
        target="_blank"
        rel="noopener noreferrer"
        className="fixed bottom-6 right-6 w-14 h-14 bg-green-500 hover:bg-green-600 text-white rounded-full flex items-center justify-center shadow-lg transition-transform transform hover:scale-110 z-50"
      >
        <FaWhatsapp className="w-8 h-8" />
      </a>


    </div>
  );
}
export default PublicLayout;
