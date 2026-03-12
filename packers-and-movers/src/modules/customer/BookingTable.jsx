import { DataGrid } from "@mui/x-data-grid";
import { Box, Button, Chip, IconButton, Typography } from "@mui/material";
import { FaRegTrashAlt } from "react-icons/fa";
import { LuPencil, LuEye } from "react-icons/lu";
const statusColorMap = {
  PENDING: "warning",
  REQUESTED: "info",
  CONFIRMED: "success",
  IN_TRANSIT: "primary",
  DELIVERED: "success",
  CANCELLED: "error",
};

const BookingTable = ({
  rows = [],
  loading,
  title,
  hideActions = false,
  onRequestCancel,
  onFinalCancel,
}) => {
  const columns = [
    // { field: "bookingId", headerName: "BOOKING ID", width: 120 },

    { field: "serviceName", headerName: "SERVICE", flex: 1 },

    { field: "pickupAddress", headerName: "PICKUP", flex: 1.5 },

    // { field: "dropAddress", headerName: "DROP", flex: 1.5 },

    { field: "moveDate", headerName: "MOVE DATE", width: 120 },

    {
      field: "status",
      headerName: "STATUS",
      width: 130,
      renderCell: ({ value }) => (
        <Chip
          label={value}
          size="small"
          color={statusColorMap[value] || "default"}
        />
      ),
    },

    {
      field: "estimatedPrice",
      headerName: "AMOUNT",
      width: 120,
      renderCell: ({ value }) => `₹${value?.toLocaleString("en-IN")}`,
    },

    !hideActions && {
      field: "actions",
      headerName: "ACTIONS",
      width: 150,
      renderCell: ({ row }) => {
        return (
          <Box>
            {row.status === "CONFIRMED" && (
              <IconButton
                size="small"
                color="warning"
                onClick={() => onRequestCancel(row.bookingId)}
              >
                <FaRegTrashAlt />
              </IconButton>
            )}

            {row.status === "PENDING" && (
              <IconButton
                size="small"
                color="error"
                onClick={() => onFinalCancel(row.bookingId)}
              >
                <FaRegTrashAlt />
              </IconButton>
            )}

            {/* View icon (always allowed) */}
            <IconButton size="small" color="info">
              <LuEye />
            </IconButton>

            {/* Edit icon (optional) */}
            <IconButton size="small" color="primary">
              <LuPencil />
            </IconButton>
          </Box>
        );
      },
    },
  ].filter(Boolean);

  return (
    <Box className="bg-white rounded-xl p-4 mb-6">
      <Typography fontWeight="bold" mb={2}>
        {title}
      </Typography>

      <DataGrid
        rows={rows}
        columns={columns}
        getRowId={(row) => row.bookingId}
        loading={loading}
        autoHeight
        hideFooter
        disableRowSelectionOnClick
      />
    </Box>
  );
};

export default BookingTable;
