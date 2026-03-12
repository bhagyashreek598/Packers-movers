import React from "react";
import { TextField, Button, Box } from "@mui/material";

const ContactForm = () => {
  const textFieldStyles = {
    "& .MuiOutlinedInput-root": {
      borderRadius: "0px",
      backgroundColor: "#fff",
      "& fieldset": {
        borderColor: "#e5e7eb",
      },
      "&:hover fieldset": {
        borderColor: "#d1d5db",
      },
      "&.Mui-focused fieldset": {
        borderWidth: "1px",
        borderColor: "#f7d53b",
      },
    },
    "& .MuiInputLabel-root.Mui-focused": {
      color: "#bfa700",
    },
  };

  const gapValue = 3;

  return (
    <div
      id="contact"
      className="bg-white py-24"
      fontFamily="Biryani,Arial,Helvetica,sans-serif"
    >
      <Box
        className="max-w-2xl px-6"
        sx={{ mx: "auto", width: 700, fontFamily: "sans-serif" }}
      >
        {/* HEADING */}
        <div className="text-center mb={gapValue * 8}px">
          <p
            className="underline decoration-yellow-400 decoration-2 underline-offset-4"
            class="text-sm mb-2 mt-5 font-medium transition-colors text-gray/700 underline decoration-yellow-400 decoration-2 underline-offset-4"
            style={{
              textAlign: "center",
              fontFamily: "'Inter', 'Roboto', sans-serif",
              fontSize: "15px",
            }}
          >
            CONTACT US
          </p>
          <h1
            class="text-sm mb-15 font-medium transition-colors text-gray/500"
            style={{
              textAlign: "center",
              fontFamily: "'Inter', 'Roboto', sans-serif",
              fontSize: "35px",
            }}
          >
            Drop us a line
          </h1>
        </div>

        {/* FORM GRID */}
        <Box
          sx={{
            display: "grid",
            gap: gapValue,
          }}
        >
          {/* NAME */}
          <TextField fullWidth label="Your Name" sx={textFieldStyles} />

          {/* TRANSPORT + SUBJECT */}
          <Box
            sx={{
              display: "grid",
              gridTemplateColumns: "1fr 1fr",
              gap: gapValue,
            }}
          >
            <TextField fullWidth label="Transport Type" sx={textFieldStyles} />
            <TextField fullWidth label="Subject" sx={textFieldStyles} />
          </Box>

          {/* MESSAGE */}
          <TextField
            fullWidth
            label="Message"
            multiline
            rows={6}
            sx={textFieldStyles}
          />

          {/* BUTTON */}
          <Button
            fullWidth
            disableElevation
            sx={{
              backgroundColor: "#3579b8",
              color: "white",
              py: 2.5,
              fontWeight: 600,
              height: 50,
              letterSpacing: "0.1em",
              borderRadius: "0px",
              transition: "background-color 0.3s ease", // smooth hover transition
              "&:hover": {
                backgroundColor: "#001f3f", // dark navy blue
                color: "#ffffff", // optional: change text color to white for contrast
              },
            }}
          >
            SEND MESSAGE
          </Button>
        </Box>
      </Box>
    </div>
  );
};

export default ContactForm;
