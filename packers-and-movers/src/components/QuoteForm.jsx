import { useState } from "react";

import {
  Box,
  Button,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  TextField,
  Typography,
  Dialog,
  DialogContent,
  IconButton,
  Divider,
} from "@mui/material";
import { styled } from "@mui/material/styles";
import DownloadIcon from "@mui/icons-material/Download";
import CloseIcon from "@mui/icons-material/Close";
import generateInvoicePDF from "./GenerateInvoicePdf";

// ===== STATIC DISTANCE & PRICING CONFIG =====

const DISTANCE_MATRIX = {
  Pune: {
    Mumbai: 150,
    Nagpur: 720,
    Bhopal: 780,
    Indore: 600,
  },
  Mumbai: {
    Pune: 150,
    Nagpur: 820,
    Bhopal: 780,
    Indore: 585,
  },
  Nagpur: {
    Pune: 720,
    Mumbai: 820,
    Bhopal: 350,
    Indore: 450,
  },
  Bhopal: {
    Pune: 780,
    Mumbai: 780,
    Nagpur: 350,
    Indore: 190,
  },
  Indore: {
    Pune: 600,
    Mumbai: 585,
    Nagpur: 450,
    Bhopal: 190,
  },
};

const SERVICE_PRICING = {
  VEHICLE: {
    vehicleName: "Small Tempo / 3 Wheeler",
    pricePerKm: 18,
  },
  HOUSE: {
    vehicleName: "Container Truck",
    pricePerKm: 30,
  },
  OFFICE: {
    vehicleName: "Large Container Truck",
    pricePerKm: 35,
  },
};

const STATE_CITY_MAP = {
  Maharashtra: ["Pune", "Mumbai", "Nagpur"],
  "Madhya Pradesh": ["Bhopal", "Indore"],
};

/* ===============================
   COMMON INPUT STYLES (UNCHANGED)
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
   LAYOUT STYLES (UNCHANGED)
================================ */
const MainContainer = styled(Box)({
  display: "flex",
  backgroundColor: "#fff",
  position: "relative",
});

const FormWrapper = styled(Box)({ flex: 1 });

const ImageContainer = styled(Box)({
  width: "420px",
  flexShrink: 0,
  position: "relative",
});

const ImageBox = styled(Box)({
  position: "absolute",
  bottom: 0,
  right: 0,
  width: "100%",
  height: "100%",
});

const SectionTitle = styled(Typography)({
  fontSize: "18px",
  fontWeight: 700,
  marginBottom: "20px",
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

const QuoteForm = () => {
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

  const [pincodeError, setPincodeError] = useState("");

  const [open, setOpen] = useState(false);
  const [invoice, setInvoice] = useState(null);

  const handleDownloadPDF = () => {
    generateInvoicePDF({
      formData,
      invoice,
    });
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((p) => ({ ...p, [name]: value }));
  };

  /* ===============================
     COST CALCULATION (SIMPLE)
================================ */
  const calculateCost = () => {
    const { pickupCity, dropCity, serviceCategory } = formData;

    if (!pickupCity || !dropCity || !serviceCategory) return null;

    // Same city not allowed
    if (pickupCity === dropCity) {
      alert("Same city moves are not available right now.");
      return null;

      if (
        formData.pickupPincode &&
        formData.dropPincode &&
        formData.pickupPincode === formData.dropPincode
      ) {
        alert("Pickup and Drop pincode cannot be same.");
        return null;
      }
    }

    const distance =
      DISTANCE_MATRIX[pickupCity]?.[dropCity] ||
      DISTANCE_MATRIX[dropCity]?.[pickupCity];

    if (!distance) return null;

    const service = SERVICE_PRICING[serviceCategory];

    const baseCost = distance * service.pricePerKm;
    const handling = 700;
    const total = baseCost + handling;

    return {
      distance,
      vehicle: service.vehicleName,
      base: baseCost,
      handling,
      total,
    };
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (
      formData.pickupPincode &&
      formData.dropPincode &&
      formData.pickupPincode === formData.dropPincode
    ) {
      setPincodeError("Pickup and Drop pincode cannot be same");
      return;
    }

    setPincodeError("");

    const cost = calculateCost();
    setInvoice(cost);
    setOpen(true);
  };

  return (
    <>
      <MainContainer
        id="quote"
        component="form"
        sx={{
          flexDirection: { xs: "column", lg: "row" },
          px: { xs: 1, sm: 2, lg: 0 },
        }}
        onSubmit={handleSubmit}
      >
        <FormWrapper sx={{ px: 4, py: 4 }}>
          {/* ---- FORM (UNCHANGED – SAME AS YOUR CODE) ---- */}
          {/* tumhara poora form yahin rahega */}
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
              InputLabelProps={{ shrink: true }}
              name="moveDate"
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
              <InputLabel id="pickup-state-label">State</InputLabel>
              <Select
                labelId="pickup-state-label"
                name="pickupState"
                value={formData.pickupState}
                label="State"
                onChange={handleChange}
              >
                <MenuItem value="Maharashtra">Maharashtra</MenuItem>
                <MenuItem value="Madhya Pradesh">Madhya Pradesh</MenuItem>
              </Select>
            </FormControl>

            <FormControl size="small" sx={commonInputSx}>
              <InputLabel>City</InputLabel>
              <Select
                name="pickupCity"
                value={formData.pickupCity}
                label="City"
                onChange={handleChange}
              >
                <MenuItem value="Pune">Pune</MenuItem>
                <MenuItem value="Mumbai">Mumbai</MenuItem>
                <MenuItem value="Nagpur">Nagpur</MenuItem>
              </Select>
            </FormControl>
          </FormRow>

          <FormRow>
            <TextField
              label="Pickup Pincode"
              name="pickupPincode"
              inputProps={{
                maxLength: 6,
                inputMode: "numeric",
                pattern: "[0-9]*",
              }}
              value={formData.pickupPincode}
              onChange={handleChange}
              error={!!pincodeError}
              helperText={pincodeError}
              sx={commonInputSx}
            />

            <TextField
              fullWidth
              label="Pickup Address"
              name="pickupAddressLine"
              value={formData.pickupAddressLine}
              onChange={handleChange}
              sx={{ ...commonInputSx, mb: 3 }}
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
              <InputLabel id="drop-state-label">State</InputLabel>
              <Select
                labelId="drop-state-label"
                name="dropState"
                value={formData.dropState}
                label="State"
                onChange={handleChange}
              >
                <MenuItem value="Maharashtra">Maharashtra</MenuItem>
                <MenuItem value="Madhya Pradesh">Madhya Pradesh</MenuItem>
              </Select>
            </FormControl>

            <FormControl size="small" sx={commonInputSx}>
              <InputLabel>City</InputLabel>
              <Select
                name="dropCity"
                value={formData.dropCity}
                label="City"
                onChange={handleChange}
              >
                {["Pune", "Mumbai", "Nagpur", "Bhopal", "Indore"]
                  .filter((city) => city !== formData.pickupCity)
                  .map((city) => (
                    <MenuItem key={city} value={city}>
                      {city}
                    </MenuItem>
                  ))}
              </Select>
            </FormControl>
          </FormRow>

          <FormRow>
            <TextField
              label="Drop Pincode"
              name="dropPincode"
              inputProps={{
                maxLength: 6,
                inputMode: "numeric",
                pattern: "[0-9]*",
              }}
              value={formData.dropPincode}
              onChange={handleChange}
              error={!!pincodeError}
              helperText={pincodeError}
              sx={commonInputSx}
            />

            <TextField
              fullWidth
              label="Drop Address"
              name="dropAddressLine"
              value={formData.dropAddressLine}
              onChange={handleChange}
              sx={{ ...commonInputSx, mb: 3 }}
            />
          </FormRow>

          <SubmitButton type="submit">Calculate Moving Cost</SubmitButton>
        </FormWrapper>

        <ImageContainer sx={{ display: { xs: "none", lg: "block" } }}>
          <ImageBox>
            <img
              src="images/form-img.png"
              alt="Courier"
              style={{
                width: "70%",
                height: "100%",
                objectFit: "contain",
                objectPosition: "bottom right",
              }}
            />
          </ImageBox>
        </ImageContainer>
      </MainContainer>

      {/* ================= INVOICE DIALOG ================= */}
      <Dialog
        open={open}
        onClose={() => setOpen(false)}
        maxWidth="sm"
        fullWidth
      >
        <DialogContent>
          {/* HEADER */}
          <Box
            display="flex"
            justifyContent="space-between"
            alignItems="center"
          >
            <Box>
              <Typography fontWeight={700} fontSize={18}>
                Packers & Movers Pvt. Ltd.
              </Typography>
              <Typography fontSize={12} color="gray">
                Estimated Invoice
              </Typography>
            </Box>

            <Box>
              <IconButton
                size="small"
                sx={{ color: "#000" }}
                onClick={handleDownloadPDF}
              >
                <DownloadIcon fontSize="small" />
              </IconButton>

              <IconButton size="small" onClick={() => setOpen(false)}>
                <CloseIcon fontSize="small" />
              </IconButton>
            </Box>
          </Box>

          <Divider sx={{ my: 2 }} />

          {/* FROM → TO */}
          <Box display="flex" justifyContent="space-between" mb={2}>
            <Box>
              <Typography fontWeight={600}>Pickup</Typography>
              <Typography fontSize={13}>{formData.pickupLabel}</Typography>
              <Typography fontSize={12} color="gray">
                {formData.pickupCity}, {formData.pickupState}
              </Typography>
            </Box>

            <Box textAlign="right">
              <Typography fontWeight={600}>Drop</Typography>
              <Typography fontSize={13}>{formData.dropLabel}</Typography>
              <Typography fontSize={12} color="gray">
                {formData.dropCity}, {formData.dropState}
              </Typography>
            </Box>
          </Box>

          <Divider sx={{ mb: 2 }} />

          {invoice && (
            <Box mb={2}>
              <Typography fontSize={14}>
                <strong>Service Type:</strong> {formData.serviceCategory}
              </Typography>
              <Typography fontSize={14}>
                <strong>Assigned Vehicle:</strong> {invoice.vehicle}
              </Typography>
              <Typography fontSize={14}>
                <strong>Distance:</strong> {invoice.distance} km
              </Typography>
            </Box>
          )}

          {/* COST TABLE */}
          {invoice && (
            <>
              {[
                ["Base Service Cost", invoice.base],
                ["Handling Charges", invoice.handling],
              ].map(([label, value]) => (
                <Box
                  key={label}
                  display="flex"
                  justifyContent="space-between"
                  mb={1}
                >
                  <Typography fontSize={14}>{label}</Typography>
                  <Typography fontSize={14}>₹ {value}</Typography>
                </Box>
              ))}

              <Divider sx={{ my: 2 }} />

              <Box
                display="flex"
                justifyContent="space-between"
                sx={{
                  background: "#f9fafb",
                  p: 1.5,
                }}
              >
                <Typography fontWeight={700}>Total Amount</Typography>
                <Typography fontWeight={700}>₹ {invoice.total}</Typography>
              </Box>
            </>
          )}
        </DialogContent>
      </Dialog>
    </>
  );
};

export default QuoteForm;
