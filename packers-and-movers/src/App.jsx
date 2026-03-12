import "./App.css";
import "./index.css";
import { Route, Routes } from "react-router";
import ThemeProvider from "./providers/ThemeProvider";
import Home from "./pages/Home";
import PublicLayout from "./layouts/PublicLayout";

import Footer from "./components/common/Footer";
import AuthProvider from "./providers/AuthProvider";
import Dashboard from "./modules/customer/Dashboard";
import Login from "./modules/auth/Login";
// import UserLayout from "./layouts/UserLayout";
import ProtectedRoute from "./auth/ProtectedRoute";
import PageNotFound from "./pages/PageNotFound";
import { useLocation } from "react-router";
import Contact from "./pages/Contact";
import SignUp from "./modules/auth/SignUp";
import Services from "./pages/Services";

//const excludedPaths = ["/footer"];

function App() {
  const location = useLocation();
  return (
    <ThemeProvider>
      <AuthProvider>
        <Routes>
          {/* Public layout */}
          <Route element={<PublicLayout />}>
            <Route index element={<Home />} />
            <Route path="home" element={<Home />} />
            <Route path="services" element={<Services />} />
            <Route path="contact" element={<Contact />} />
            <Route path="footer" element={<Footer />} />
          </Route>

          {/* Auth */}
          <Route path="login" element={<Login />} />
          <Route path="signup" element={<SignUp />} />

          {/* Protected */}
          <Route
            path="dashboard"
            element={
              <ProtectedRoute>
                <Dashboard />
              </ProtectedRoute>
            }
          />

          {/* 404 */}
          <Route path="*" element={<PageNotFound />} />
        </Routes>
      </AuthProvider>
    </ThemeProvider>
  );
}

export default App;
