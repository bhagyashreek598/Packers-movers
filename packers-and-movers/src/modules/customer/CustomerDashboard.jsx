import React, { useState, useEffect } from "react";
import BookingTable from "./BookingTable";
import HomeIcon from "@mui/icons-material/Home";
import {
  TextField,
  Button,
  Box,
  Avatar,
  Tabs,
  Tab,
  Card,
  IconButton,
  InputBase,
  Paper,
  Divider,
  Typography,
} from "@mui/material";
import {
  Logout,
  Email,
  Add,
  LocalShippingOutlined,
  AssignmentOutlined,
  HistoryOutlined,
} from "@mui/icons-material";
import { useNavigate } from "react-router";

// --- Data Arrays ---
const activeBookingsData = [
  { id: 1, bookingId: "BK-9921", serviceName: "Home Shifting (3BHK)", scheduledDate: "2026-03-25", status: "Confirmed", totalAmount: 15500 },
  { id: 2, bookingId: "BK-8842", serviceName: "Office Relocation", scheduledDate: "2026-03-28", status: "Pending", totalAmount: 45000 },
  { id: 3, bookingId: "BK-7712", serviceName: "Furniture Delivery", scheduledDate: "2026-04-01", status: "Confirmed", totalAmount: 2500 },
];

const pastData = [
  { id: 101, bookingId: "BK-5520", serviceName: "Bike Transport", scheduledDate: "2025-12-01", status: "Confirmed", totalAmount: 8500 },
  { id: 102, bookingId: "BK-4412", serviceName: "Vehicle Transport", scheduledDate: "2025-11-15", status: "Pending", totalAmount: 12000 },
  { id: 103, bookingId: "BK-3305", serviceName: "Local Move", scheduledDate: "2025-10-20", status: "Completed", totalAmount: 5000 },
  { id: 104, bookingId: "BK-3309", serviceName: "Intercity Move", scheduledDate: "2025-09-10", status: "Completed", totalAmount: 22000 },
];

const CustomerDashboard = () => {
  const [user, setUser] = useState(null);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [activeTab, setActiveTab] = useState(0);

  const navigate = useNavigate();

  useEffect(() => {
    const loggedInUser = localStorage.getItem("customer_user");
    if (loggedInUser) setUser(JSON.parse(loggedInUser));
  }, []);

  const handleLogin = (e) => {
    e.preventDefault();
    if (email && password) {
      const userData = { email, name: "Mathew Anderson" };
      localStorage.setItem("customer_user", JSON.stringify(userData));
      setUser(userData);
    }
  };

  const handleLogout = () => {
    localStorage.removeItem("customer_user");
    setUser(null);
  };

  /* ---------------- LOGIN SCREEN ---------------- */
  if (!user) {
    return (
      <div
        className="min-h-screen flex items-center justify-center p-4 bg-cover bg-center relative"
        style={{ backgroundImage: "url('/src/assets/images/login-bg.png')" }}
      >
        <div className="absolute inset-0 bg-gray-300/60" />

        <Box className="relative w-full max-w-md bg-white p-10 shadow-xl rounded-xl border border-gray-100 z-10">
          <div className="text-center mb-6">
            <Typography variant="h4" className="font-bold text-gray-800">
              User Login
            </Typography>
            <Typography className="text-gray-500 mt-2">
              Access your shipping dashboard
            </Typography>
          </div>

          <form onSubmit={handleLogin}>
            <Box className="mb-4">
              <TextField fullWidth label="Email Address" size="small" onChange={(e) => setEmail(e.target.value)} />
            </Box>

            <Box className="mb-5">
              <TextField fullWidth label="Password" type="password" size="small" onChange={(e) => setPassword(e.target.value)} />
            </Box>

            <Button
              fullWidth
              type="submit"
              sx={{
                color: "#fff",
                backgroundColor: "#0D3B66",
                py: 1.4,
                fontWeight: "bold",
                "&:hover": { backgroundColor: "#0D3B66" },
              }}
            >
              LOGIN
            </Button>

            <p className="text-center mt-4 text-[#0D3B66] underline decoration-yellow-400 decoration-2 hover:decoration-yellow-500 cursor-pointer">
              <a href="/">Back to the website</a>
            </p>
          </form>
        </Box>
      </div>
    );
  }

  /* ---------------- DASHBOARD ---------------- */
  return (
    <div className="min-h-screen bg-[#f4f7fe] p-4 md:p-8">
      <div className="max-w-5xl mx-auto space-y-6">

        {/* Header */}
        <Paper className="p-4 flex justify-between items-center shadow-sm rounded-xl">
          <button
            onClick={() => navigate("/")}
            className="flex items-center gap-2 px-3 py-2 rounded-full text-[#1171BA] hover:bg-blue-100 transition-all"
          >
            <HomeIcon />
            <span className="font-semibold">Home</span>
          </button>

          <div className="flex items-center gap-3">
            <Typography className="font-bold text-gray-700">User Profile</Typography>
            <IconButton onClick={handleLogout} size="small" color="error">
              <Logout fontSize="small" />
            </IconButton>
          </div>
        </Paper>

        {/* Profile Card */}
        <Card className="rounded-2xl overflow-hidden shadow-sm border-none">
          <div className="h-48 bg-gradient-to-r from-indigo-200 to-purple-200 relative">
            <img
              src="https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?auto=format&fit=crop&w=1000&q=80"
              className="w-full h-full object-cover opacity-60"
              alt="cover"
            />
          </div>

          <div className="px-8 pb-6 relative">
            <div className="flex flex-col md:flex-row items-center justify-between mt-[-40px]">
              <div className="text-center">
                <Avatar
                  src="https://cdn-icons-png.flaticon.com/512/149/149071.png"
                  className="w-24 h-24 border-4 border-white shadow-lg mx-auto mb-2"
                />
                <Typography variant="h6" className="font-bold text-gray-800">
                  {user.name}
                </Typography>
              </div>

              <Button variant="contained" startIcon={<Add />} sx={{ mt: { xs: 2, md: 6 } }}>
                Book a Move
              </Button>
            </div>
          </div>

          <Divider />

          <Tabs value={activeTab} onChange={(e, val) => setActiveTab(val)} centered>
            <Tab icon={<LocalShippingOutlined />} iconPosition="start" label="Active Bookings" />
            <Tab icon={<AssignmentOutlined />} iconPosition="start" label="Booking Details" />
            <Tab icon={<HistoryOutlined />} iconPosition="start" label="Past Bookings" />
          </Tabs>
        </Card>

        {/* Tabs Content */}
        <Box>
          {activeTab === 0 && <BookingTable rows={activeBookingsData} onlyShowCompleted={false} />}
          {activeTab === 1 && (
            <Card className="p-12 text-center rounded-2xl bg-white shadow-sm border-none">
              <AssignmentOutlined sx={{ fontSize: 48, color: "#cbd5e1", mb: 2 }} />
              <Typography className="text-gray-500">
                Select a shipment to view detailed information.
              </Typography>
            </Card>
          )}
          {activeTab === 2 && <BookingTable rows={pastData} onlyShowCompleted />}
        </Box>

        {/* Bottom Section */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <Card className="p-6 rounded-2xl shadow-sm border-none">
            <Typography className="font-bold mb-4">Hello, {user.name}</Typography>
            <div className="flex items-center gap-3 text-sm text-gray-600">
              <Email fontSize="small" /> {user.email}
            </div>
          </Card>

          <Card className="md:col-span-2 p-4 rounded-2xl shadow-sm border-none">
            <InputBase
              placeholder="How was your last move?"
              fullWidth
              multiline
              rows={2}
              className="bg-gray-50 p-3 rounded-xl mb-4"
            />
            <div className="flex justify-end">
              <Button variant="contained">Submit Feedback</Button>
            </div>
          </Card>
        </div>
      </div>
    </div>
  );
};

export default CustomerDashboard;
