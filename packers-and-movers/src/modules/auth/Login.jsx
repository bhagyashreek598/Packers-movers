import { useState } from "react";
import { Box, TextField, Button, Typography } from "@mui/material";
import { Navigate, useNavigate } from "react-router";
import { signinUser } from "../../services/auth";
import { useAuth } from "../../providers/AuthProvider";
import { successAlert } from "../../utils/swalAlerts";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const { setUser } = useAuth();

  const handleLogin = async (e) => {
    e.preventDefault();

    if (!email || !password) {
      alert("Email and password required");
      return;
    }

    try {
      const credentials = { email, password };
      const response = await signinUser(credentials);

      // dashboard will remain there
      if (!response?.token) {
        alert("Invalid email or password");
        return;
      }

      sessionStorage.setItem("token", response.token);
      sessionStorage.setItem("user", JSON.stringify(response));
      setUser(response);

      // (successAlert((title = "Login successful")), (text = "sdjsdhsjs"));
      successAlert({
        title: "Welcome to Dashboard !",
        text: "Click ok to proceed",
      });
      navigate("/dashboard");
    } catch (error) {
      alert("Login failed");
    }
  };

  return (
    <div
      className="min-h-screen flex items-center justify-center p-4 bg-cover bg-center relative"
      style={{ backgroundImage: "url('images/login-bg.png')" }}
    >
      <div className="absolute inset-0 bg-gray-300/60" />

      <Box className="relative w-full max-w-md bg-white p-10 shadow-xl rounded-xl z-10">
        <div className="text-center mb-6">
          <Typography variant="h4" fontWeight="bold">
            User Login
          </Typography>
          <Typography color="text.secondary">
            Access your shipping dashboard
          </Typography>
        </div>

        <form onSubmit={handleLogin}>
          <TextField
            fullWidth
            label="Email Address"
            size="small"
            margin="normal"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />

          <TextField
            fullWidth
            label="Password"
            type="password"
            size="small"
            margin="normal"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />

          <Button
            fullWidth
            type="submit"
            sx={{ mt: 2, py: 1.4, fontWeight: "bold" }}
            variant="contained"
          >
            LOGIN now
          </Button>
        </form>
        {/* <div className="text-center mt-6">
          <Typography color="text.secondary">
            Click{" "}
            <a className="text-blue-600" href="/signup">
              SignUp
            </a>{" "}
            if new user
          </Typography>
        </div> */}
        <div className="text-center mt-6">
          <Button
            fullWidth
            type="button"
            sx={{ mt: 2, py: 1.4, fontWeight: "bold" }}
            variant="contained"
            onClick={() => navigate("/signup")}
          >
            Signup
          </Button>
        </div>
      </Box>
    </div>
  );
};

export default Login;
