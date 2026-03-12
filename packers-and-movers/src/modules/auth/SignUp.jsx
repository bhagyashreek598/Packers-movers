import { useState } from "react";
import { Box, TextField, Button, Typography } from "@mui/material";
import { useNavigate } from "react-router";
import { signupCustomer } from "../../services/auth";

const SignUp = () => {
  const [initialState, setInitialState] = useState({
    name: "",
    phone: "",
    email: "",
    password: "",
  });
  let confirmPassword;

  const navigate = useNavigate();
  const handleChange = (event) => {
    const { name, value } = event.target;
    setInitialState((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleChangeConfirmPassword = (e) => {
    console.log("confr pass: ", e.target.value);
  };

  const handleSgnUp = async (e) => {
    e.preventDefault();

    try {
      const response = await signupCustomer(initialState);
      console.log("response..", response);

      navigate("/login");
    } catch (error) {
      alert("Signup failed");
    }
  };

  return (
    <div
      className="min-h-screen flex items-center justify-center p-4 bg-cover bg-center relative"
      style={{ backgroundImage: "url('/src/assets/images/login-bg.png')" }}
    >
      <div className="absolute inset-0 bg-gray-300/60" />

      <Box className="relative w-full max-w-md bg-white p-10 shadow-xl rounded-xl z-10">
        <div className="text-center mb-6">
          <Typography variant="h4" fontWeight="bold">
            User SignUp
          </Typography>
          <Typography color="text.secondary">
            Create your shipping account
          </Typography>
        </div>

        <form onSubmit={handleSgnUp}>
          <TextField
            fullWidth
            label="Your Name"
            size="small"
            name="name"
            margin="normal"
            value={initialState.name}
            onChange={handleChange}
          />

          <TextField
            fullWidth
            type="number"
            label="Mobile Number"
            size="small"
            margin="normal"
            name="phone"
            value={initialState.phone}
            onChange={handleChange}
          />

          <TextField
            fullWidth
            type="email"
            label="Email Address"
            size="small"
            margin="normal"
            name="email"
            value={initialState.email}
            onChange={handleChange}
          />

          <TextField
            fullWidth
            label="Password"
            type="password"
            size="small"
            name="password"
            margin="normal"
            value={initialState.password}
            onChange={handleChange}
          />

          <TextField
            fullWidth
            label="Confirm password"
            type="text"
            size="small"
            name="password"
            margin="normal"
            value={confirmPassword}
            onChange={handleChangeConfirmPassword}
          />

          <Button
            fullWidth
            type="submit"
            sx={{ mt: 2, py: 1.4, fontWeight: "bold" }}
            variant="contained"
          >
            Create Account
          </Button>
        </form>
        <div className="text-center mt-4 mb-3">
          <Typography color="text.secondary">
            {" "}
            <a className="text-blue-600" href="/login">
              Login
            </a>{" "}
            if registered already
          </Typography>
        </div>
      </Box>
    </div>
  );
};

export default SignUp;
