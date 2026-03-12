import React, { useState, useEffect } from "react";
import { motion, AnimatePresence } from "framer-motion";
import MenuIcon from "@mui/icons-material/Menu";
import CloseIcon from "@mui/icons-material/Close";
import PhoneIcon from "@mui/icons-material/Phone";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";
import DashboardIcon from "@mui/icons-material/Dashboard";
import { useNavigate } from "react-router";
import { useAuth } from "./../../providers/AuthProvider";

const Navbar = () => {
  const [isScrolled, setIsScrolled] = useState(false);
  const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);
  const { user } = useAuth();
  useEffect(() => {
    const handleScroll = () => {
      setIsScrolled(window.scrollY > 50);
    };
    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);
  const navigate = useNavigate();

  const scrollToSection = (id) => {
    const element = document.getElementById(id);
    if (element) {
      element.scrollIntoView({ behavior: "smooth" });
    }
  };

  const navLinks = [
    { name: "Home", type: "route", path: "/" },
    { name: "Services", type: "scroll", id: "services" },
    { name: "Process", type: "scroll", id: "process" },
    // { name: "Tracking", type: "scroll", id: "tracking" },
    { name: "Contact", type: "route", path: "/contact" },
  ];

  return (
    <nav
      className={`fixed w-full z-50 transition-all duration-500 ${
        isScrolled ? "bg-white py-3 shadow-md" : "bg-transparent py-6"
      }`}
    >
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center">
          {/* Logo Section */}
          <div className="flex-shrink-0">
            <span className="text-2xl font-bold transition-colors text-white">
              <img src="images/logo.png" width="100px" />
              {/* LOGI<span className="text-[#1171BA]">MOVE</span> */}
            </span>
          </div>

          {/* Desktop Navigation */}
          <div className="hidden md:flex items-center space-x-8">
            {navLinks.map((link) => (
              <button
                key={link.name}
                onClick={() => {
                  if (link.type === "route") {
                    navigate(link.path);
                  } else {
                    scrollToSection(link.id);
                  }
                }}
                className={`text-sm font-medium transition-colors hover:text-[#1171BA] ${
                  isScrolled ? "text-gray-900" : "text-gray/700"
                }`}
              >
                {link.name}
              </button>
            ))}

            {/* MUI Icon within Tailwind Button */}
            <button
              onClick={() => scrollToSection("quote")}
              className="flex items-center gap-2 bg-[#1171BA] hover:bg-[#0D3B66] text-white px-5 py-2 rounded font-bold transition-all active:scale-95"
            >
              Get Quote
            </button>

            <button
              onClick={() => scrollToSection("quote")}
              className="flex items-center gap-2 bg-[#1171BA] hover:bg-[#0D3B66] text-white px-5 py-2 rounded font-bold transition-all active:scale-95"
            >
              Book a new move
            </button>

            {user ? (
              <button
                onClick={() => navigate("/dashboard")}
                className="flex items-center gap-2 text-[#1171BA] hover:text-[#0D3B66] px-5 py-2 rounded border-2 border-[#1171BA] hover:border-[#0D3B66] font-bold transition-all active:scale-95"
              >
                <DashboardIcon sx={{ fontSize: 18 }} />
                Track shipment
              </button>
            ) : (
              <button
                onClick={() => navigate("/login")}
                className="flex items-center gap-2 text-[#1171BA] hover:text-[#0D3B66] px-5 py-2 rounded border-2 border-[#1171BA] hover:border-[#0D3B66] font-bold transition-all active:scale-95"
              >
                <DashboardIcon sx={{ fontSize: 18 }} />
                Signup/Login
              </button>
            )}
          </div>

          {/* Mobile Menu Toggle */}
          <div className="md:hidden flex items-center">
            <button
              onClick={() => setIsMobileMenuOpen(!isMobileMenuOpen)}
              className={isScrolled ? "text-[#0D3B66]" : "text-white"}
            >
              {isMobileMenuOpen ? <CloseIcon /> : <MenuIcon />}
            </button>
          </div>
        </div>
      </div>

      {/* Mobile Menu Overlay */}
      <AnimatePresence>
        {isMobileMenuOpen && (
          <motion.div
            initial={{ opacity: 0, y: -20 }}
            animate={{ opacity: 1, y: 0 }}
            exit={{ opacity: 0, y: -20 }}
            className="md:hidden bg-white border-t border-gray-100"
          >
            <div className="px-4 py-6 space-y-4">
              {navLinks.map((link) => (
                <button
                  key={link.name}
                  onClick={() => {
                    setIsMobileMenuOpen(false);
                    if (link.type === "route") {
                      navigate(link.path);
                    } else {
                      scrollToSection(link.id);
                    }
                  }}
                  className="block w-full text-left text-gray-800 font-medium hover:text-[#1171BA]"
                >
                  {link.name}
                </button>
              ))}

              {/* CTA Button */}
              <button
                onClick={() => {
                  setIsMobileMenuOpen(false);
                  scrollToSection("quote");
                }}
                className="w-full bg-[#0D3B66] text-white py-3 rounded font-bold flex justify-center items-center gap-2"
              >
                Get Quote <ChevronRightIcon />
              </button>

              {user ? (
                <button
                  onClick={() => {
                    setIsMobileMenuOpen(false);
                    navigate("/dashboard");
                  }}
                  className="w-full border-2 border-[#1171BA] text-[#1171BA] py-3 rounded font-bold"
                >
                  Track Shipment
                </button>
              ) : (
                <button
                  onClick={() => {
                    setIsMobileMenuOpen(false);
                    navigate("/login");
                  }}
                  className="w-full border-2 border-[#1171BA] text-[#1171BA] py-3 rounded font-bold"
                >
                  Signup / Login
                </button>
              )}
            </div>
          </motion.div>
        )}
      </AnimatePresence>
    </nav>
  );
};

export default Navbar;
