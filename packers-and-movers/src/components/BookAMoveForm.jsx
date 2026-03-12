import { useState, useEffect } from "react";
import {
  Box,
  Button,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  TextField,
  Typography,
} from "@mui/material";
import { styled } from "@mui/material/styles";
import { createBooking } from "../services/booking";
import { getStates, getCitiesByState } from "../services/location";

/* ===============================
   COMMON INPUT STYLES
================================ */
const commonInputSx = {
  "& .MuiOutlinedInput-root": {
    height: "44px",
    fontSize: "14px",
    borderRadius: "2px",
    backgroundColor: "#fff",
    "& fieldset": { borderColor: "#e5e7eb" },
    "&:hover fieldset": { borderColor: "#d1d5db" },
    "&.Mui-focused fieldset": {
      borderColor: "#000",
      borderWidth: "1px",
    },
  },
  "& .MuiInputLabel-root": {
    fontSize: "13px",
    color: "#9ca3af",
    top: "-2px",
  },
  "& .MuiInputLabel-root.Mui-focused": {
    color: "#000",
  },
};

/* ===============================
   LAYOUT STYLES
================================ */
const MainContainer = styled(Box)({
  display: "flex",
  backgroundColor: "#fff",
  position: "relative",
});

const FormWrapper = styled(Box)({
  flex: 1,
});

const SectionTitle = styled(Typography)({
  fontSize: "18px",
  fontWeight: 700,
  marginBottom: "20px",
  color: "#000",
});

const FormRow = styled(Box)({
  display: "grid",
  gridTemplateColumns: "repeat(3, 1fr)",
  gap: "20px",
  marginBottom: "20px",
});

const SubmitButton = styled(Button)({
  backgroundColor: "#111827",
  color: "#fff",
  height: "48px",
  fontSize: "14px",
  fontWeight: 600,
  borderRadius: "0",
  width: "100%",
  maxWidth: "420px",
  margin: "0 auto",
  display: "block",
  "&:hover": { backgroundColor: "#000" },
});

/* ===============================
   COMPONENT
================================ */
const BookAMoveForm = ({ onClose }) => {
  /* ---------- API DATA STATES ---------- */
  const [states, setStates] = useState([]);
  const [pickupCities, setPickupCities] = useState([]);
  const [dropCities, setDropCities] = useState([]);

  /* ---------- FORM STATE ---------- */
  const [formData, setFormData] = useState({
    serviceCategory: "",
    pickupLabel: "",
    pickupAddressLine: "",
    pickupState: "",
    pickupCity: "",
    pickupPincode: "",
    dropLabel: "",
    dropAddressLine: "",
    dropState: "",
    dropCity: "",
    dropPincode: "",
    moveDate: "",
  });

  /* ---------- FETCH STATES ---------- */
  useEffect(() => {
    getStates()
      .then((res) => setStates(res))
      .catch((err) => console.error("State fetch failed", err));
  }, []);

  /* ---------- COMMON CHANGE ---------- */
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((p) => ({ ...p, [name]: value }));
  };

  /* ---------- PICKUP STATE ---------- */
  const handlePickupStateChange = async (e) => {
    const state = e.target.value;

    setFormData((p) => ({
      ...p,
      pickupState: state,
      pickupCity: "",
    }));

    try {
      const cities = await getCitiesByState(state);
      setPickupCities(cities);
    } catch (err) {
      console.error(err);
    }
  };

  /* ---------- DROP STATE ---------- */
  const handleDropStateChange = async (e) => {
    const state = e.target.value;

    setFormData((p) => ({
      ...p,
      dropState: state,
      dropCity: "",
    }));

    try {
      const cities = await getCitiesByState(state);
      setDropCities(cities);
    } catch (err) {
      console.error(err);
    }
  };

  /* ---------- SUBMIT ---------- */
  const handleSubmit = async (e) => {
    e.preventDefault();

    const payload = {
      ...formData,
      estimatedPrice: 40000,
    };

    try {
      await createBooking(payload);
      alert("Booking created successfully!");
      onClose?.();
    } catch (err) {
      console.error("Booking failed:", err);
      alert("Something went wrong!");
    }
  };

  return (
    <MainContainer
      component="form"
      onSubmit={handleSubmit}
      sx={{ flexDirection: { xs: "column", lg: "row" } }}
    >
      <FormWrapper sx={{ px: 4, py: 4 }}>
        {/* SERVICE */}
        <SectionTitle>Service Details and Schedule</SectionTitle>
        <FormRow>
          <FormControl size="small" sx={commonInputSx}>
            <InputLabel>Service Category</InputLabel>
            <Select
              name="serviceCategory"
              value={formData.serviceCategory}
              label="Service Category"
              onChange={handleChange}
            >
              <MenuItem value="VEHICLE">Vehicle</MenuItem>
              <MenuItem value="HOUSE">House</MenuItem>
              <MenuItem value="OFFICE">Office</MenuItem>
            </Select>
          </FormControl>

          <TextField
            type="date"
            label="Move Date"
            name="moveDate"
            InputLabelProps={{ shrink: true }}
            value={formData.moveDate}
            onChange={handleChange}
            sx={commonInputSx}
          />
        </FormRow>

        {/* PICKUP */}
        <SectionTitle>Pickup Details</SectionTitle>
        <FormRow>
          <TextField
            label="Pickup Label"
            name="pickupLabel"
            value={formData.pickupLabel}
            onChange={handleChange}
            sx={commonInputSx}
          />

          <FormControl size="small" sx={commonInputSx}>
            <InputLabel>State</InputLabel>
            <Select
              value={formData.pickupState}
              label="State"
              onChange={handlePickupStateChange}
            >
              {states.map((s) => (
                <MenuItem key={s} value={s}>
                  {s}
                </MenuItem>
              ))}
            </Select>
          </FormControl>

          <FormControl
            size="small"
            sx={commonInputSx}
            disabled={!pickupCities.length}
          >
            <InputLabel>City</InputLabel>
            <Select
              name="pickupCity"
              value={formData.pickupCity}
              label="City"
              onChange={handleChange}
            >
              {pickupCities.map((c) => (
                <MenuItem key={c} value={c}>
                  {c}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </FormRow>

        <FormRow>
          <TextField
            label="Pickup Pincode"
            name="pickupPincode"
            value={formData.pickupPincode}
            onChange={handleChange}
            sx={commonInputSx}
          />

          <TextField
            fullWidth
            label="Pickup Address"
            name="pickupAddressLine"
            value={formData.pickupAddressLine}
            onChange={handleChange}
            sx={commonInputSx}
          />
        </FormRow>

        {/* DROP */}
        <SectionTitle>Drop Details</SectionTitle>
        <FormRow>
          <TextField
            label="Drop Label"
            name="dropLabel"
            value={formData.dropLabel}
            onChange={handleChange}
            sx={commonInputSx}
          />

          <FormControl size="small" sx={commonInputSx}>
            <InputLabel>State</InputLabel>
            <Select
              value={formData.dropState}
              label="State"
              onChange={handleDropStateChange}
            >
              {states.map((s) => (
                <MenuItem key={s} value={s}>
                  {s}
                </MenuItem>
              ))}
            </Select>
          </FormControl>

          <FormControl
            size="small"
            sx={commonInputSx}
            disabled={!dropCities.length}
          >
            <InputLabel>City</InputLabel>
            <Select
              name="dropCity"
              value={formData.dropCity}
              label="City"
              onChange={handleChange}
            >
              {dropCities.map((c) => (
                <MenuItem key={c} value={c}>
                  {c}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </FormRow>

        <FormRow>
          <TextField
            label="Drop Pincode"
            name="dropPincode"
            value={formData.dropPincode}
            onChange={handleChange}
            sx={commonInputSx}
          />

          <TextField
            fullWidth
            label="Drop Address"
            name="dropAddressLine"
            value={formData.dropAddressLine}
            onChange={handleChange}
            sx={commonInputSx}
          />
        </FormRow>

        <SubmitButton type="submit">Book Now</SubmitButton>
      </FormWrapper>
    </MainContainer>
  );
};

export default BookAMoveForm;
