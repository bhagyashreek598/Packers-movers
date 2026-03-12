import { Box, Typography, Button } from "@mui/material";
import { useNavigate } from "react-router";

const PageNotFound = () => {
  const navigate = useNavigate();

  return (
    <Box
      sx={{
        minHeight: "100vh",
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        textAlign: "center",
      }}
    >
      <Typography variant="h2" fontWeight="bold">
        404
      </Typography>
      <Typography sx={{ mb: 3 }}>Page not found</Typography>
      <Button variant="contained" onClick={() => navigate("/login")}>
        Go to Login
      </Button>
    </Box>
  );
};

export default PageNotFound;
