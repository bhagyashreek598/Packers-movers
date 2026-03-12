import { useEffect, useState } from "react";
import BookingTable from "./BookingTable";

import HomeIcon from "@mui/icons-material/Home";
import {
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
  Button,
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
import { useAuth } from "../../providers/AuthProvider";

import BookMoveDialog from "../../components/common/BookMoveDialog.jsx";
import {
  getBookingHistory,
  getCurrentBookings,
} from "../../services/booking.js";

/* ---------------- DUMMY DATA ---------------- */

// const activeBookingsData = [
//   {
//     id: 1,
//     bookingId: "BK-9921",
//     serviceName: "Home Shifting (3BHK)",
//     scheduledDate: "2026-03-25",
//     status: "Confirmed",
//     totalAmount: 15500,
//   },
//   {
//     id: 2,
//     bookingId: "BK-8842",
//     serviceName: "Office Relocation",
//     scheduledDate: "2026-03-28",
//     status: "Pending",
//     totalAmount: 45000,
//   },
//   {
//     id: 3,
//     bookingId: "BK-7712",
//     serviceName: "Furniture Delivery",
//     scheduledDate: "2026-04-01",
//     status: "Confirmed",
//     totalAmount: 2500,
//   },
// ];

const pastBookingsData = [
  {
    id: 101,
    bookingId: "BK-5520",
    serviceName: "Bike Transport",
    scheduledDate: "2025-12-01",
    status: "Completed",
    totalAmount: 8500,
  },
  {
    id: 102,
    bookingId: "BK-4412",
    serviceName: "Vehicle Transport",
    scheduledDate: "2025-11-15",
    status: "Completed",
    totalAmount: 12000,
  },
];

/* ---------------- COMPONENT ---------------- */
// console.log("hello dash board");
// console.log("user details in session storedUser", user);
const Dashboard = () => {
  const [activeTab, setActiveTab] = useState(0);
  const { user, logout } = useAuth();
  const [openBookDialog, setOpenBookDialog] = useState(false);
  const [activeBookings, setActiveBookings] = useState([]);
  const [historyBookings, setHistoryBookings] = useState([]);
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();

  console.log("recent bookings: ", activeBookings);
  console.log("closed bookings: ", historyBookings);
  // LOAD BOOKINGS
  const loadBookings = async () => {
    setLoading(true);
    try {
      const [current, history] = await Promise.all([
        getCurrentBookings(),
        getBookingHistory(),
      ]);

      setActiveBookings(current);
      setHistoryBookings(history);
    } catch (err) {
      console.error("Failed to load bookings", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadBookings();
  }, []);

  return (
    <div>
      <div className="min-h-screen bg-[#f4f7fe] p-4 md:p-8">
        <div className="max-w-5xl mx-auto space-y-6">
          {/* ================= HEADER ================= */}
          <Paper className="p-4 flex justify-between items-center rounded-xl shadow-sm">
            <div
              onClick={() => navigate("/home")}
              className="flex items-center gap-2 text-[#1171BA] font-semibold cursor-pointer"
            >
              <HomeIcon />
              Home
            </div>
            <IconButton color="error" onClick={logout}>
              <Logout />
            </IconButton>
          </Paper>

          {/* ================= PROFILE CARD ================= */}
          <Card className="rounded-2xl overflow-hidden shadow-sm border-none">
            <div className="h-40 bg-gradient-to-r from-indigo-200 to-purple-200" />

            <div className="px-6 pb-6">
              <div className="flex flex-col md:flex-row items-center justify-between mt-[-40px]">
                <div className="text-center">
                  <Avatar
                    src="https://cdn-icons-png.flaticon.com/512/149/149071.png"
                    sx={{ width: 96, height: 96 }}
                    className="border-4 border-white shadow-lg mx-auto"
                  />
                  <Typography variant="h6" fontWeight="bold" mt={1}>
                    {user?.name}
                  </Typography>
                </div>

                <Button
                  variant="contained"
                  startIcon={<Add />}
                  sx={{ mt: { xs: 2, md: 6 } }}
                  onClick={() => setOpenBookDialog(true)}
                >
                  Book a Move
                </Button>
              </div>
            </div>

            <Divider />

            <Tabs
              value={activeTab}
              onChange={(e, v) => setActiveTab(v)}
              centered
            >
              <Tab
                icon={<LocalShippingOutlined />}
                iconPosition="start"
                label="Active Bookings"
              />
              <Tab
                icon={<AssignmentOutlined />}
                iconPosition="start"
                label="Booking Details"
              />
              <Tab
                icon={<HistoryOutlined />}
                iconPosition="start"
                label="Past Bookings"
              />
            </Tabs>
          </Card>

          {/* ================= TAB CONTENT ================= */}
          <Box>
            {activeTab === 0 && (
              // <BookingTable rows={activeBookings} />
              <BookingTable
                title="Active Bookings"
                rows={activeBookings}
                loading={loading}
                // onRequestCancel={handleRequestCancel}
                // onFinalCancel={handleFinalCancel}
              />
            )}

            {activeTab === 1 && (
              <Card className="p-10 text-center rounded-2xl shadow-sm">
                <AssignmentOutlined
                  sx={{ fontSize: 48, color: "#cbd5e1", mb: 2 }}
                />
                <Typography color="text.secondary">
                  Select a booking to view detailed information
                </Typography>
              </Card>
            )}

            {activeTab === 2 && <BookingTable rows={pastBookingsData} />}
          </Box>

          {/* ================= BOTTOM SECTION ================= */}
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            <Card className="p-6 rounded-2xl shadow-sm border-none">
              <Typography fontWeight="bold" mb={2}>
                Contact Info
              </Typography>

              <Typography className="flex items-center gap-2 text-gray-600">
                <Email fontSize="small" />
                {user?.email}
              </Typography>
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
      {/* dialog */}
      <BookMoveDialog
        open={openBookDialog}
        setOpenBookDialog={setOpenBookDialog}
        onClose={() => setOpenBookDialog(false)}
      />
    </div>
  );
};

export default Dashboard;
