import { Navigate } from "react-router";
import { useAuth } from "../providers/AuthProvider";

const ProtectedRoute = ({ children }) => {
  const { user, loading } = useAuth();

  if (loading) return null;
  if (!user) return <Navigate to="/login" replace />;

  return children;
};

export default ProtectedRoute;
