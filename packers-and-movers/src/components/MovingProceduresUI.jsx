import { Grid, Box, Typography } from "@mui/material";
import Inventory2OutlinedIcon from "@mui/icons-material/Inventory2Outlined";
import LocalShippingOutlinedIcon from "@mui/icons-material/LocalShippingOutlined";
import { steps } from "../utils/dataSet";

const fontStyle = {
  fontFamily: "'Inter', sans-serif",
};

const iconMap = {
  inventory: Inventory2OutlinedIcon,
  shipping: LocalShippingOutlinedIcon,
};

const MovingProceduresUI = () => {
  return (
    <Box
      id="process"
      className="w-full py-20"
      sx={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        flexDirection: "column",
        backgroundColor: "#F2F2F2",
      }}
    >
      {/* Heading */}
      <Box className="text-center mb-14">
        <Typography
          sx={{
            ...fontStyle,
            fontSize: "13px",
            letterSpacing: "2px",
            fontWeight: 600,
            color: "#111827",
          }}
        >
          WE MOVING SAFELY
        </Typography>

        <Typography
          sx={{
            ...fontStyle,
            fontSize: { xs: "28px", md: "42px" },
            fontWeight: 700,
            marginTop: "10px",
            color: "#111827",
          }}
        >
          Safe Moving <span style={{ fontWeight: 800 }}>Procedures</span>
        </Typography>
      </Box>

      {/* Cards */}
      <Grid container spacing={3} justifyContent={"center"}>
        {steps.map((item, index) => {
          const Icon = iconMap[item.icon];

          return (
            <Grid item xs={12} md={4} key={index}>
              <Box
                className="
                  group
                  h-full
                  bg-white
                  border border-[#E5E7EB]
                  transition-all duration-300
                  hover:bg-[#1171BA]
                  hover:text-white 
                "
                sx={{
                  minHeight: "260px",
                  padding: "40px",
                  position: "relative",
                }}
              >
                {/* Step number */}
                <Typography
                  sx={{
                    ...fontStyle,
                    position: "absolute",
                    top: 20,
                    right: 25,
                    fontSize: "18px",
                    fontWeight: 700,
                    color: "#E5E7EB",
                  }}
                  className="group-hover:text-white"
                >
                  {item.step}
                </Typography>

                {/* Title */}
                <Typography
                  sx={{
                    ...fontStyle,
                    whiteSpace: "pre-line",
                    fontSize: "22px",
                    fontWeight: 700,
                    lineHeight: "32px",
                  }}
                >
                  {item.title}
                </Typography>

                {/* Bottom section */}
                <Box className="flex items-center justify-between mt-12">
                  <Typography
                    sx={{
                      ...fontStyle,
                      fontSize: "14px",
                    }}
                  >
                    Explore the features
                  </Typography>

                  <Icon fontSize="large" className="text-inherit" />
                </Box>
              </Box>
            </Grid>
          );
        })}
      </Grid>
    </Box>
  );
};

export default MovingProceduresUI;
