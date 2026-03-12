import React, { useState, useEffect } from "react";
import { DataGrid } from "@mui/x-data-grid";
import { Box, Paper, Chip, Typography } from "@mui/material";

const columns_old = [
  {
    field: "serviceName",
    headerName: "PRODUCT (SERVICE)",
    flex: 1.5,
    minWidth: 200,
    renderCell: (params) => (
      <Typography className="text-sm font-medium text-gray-700">
        {params.value}
      </Typography>
    ),
  },
  { field: "bookingId", headerName: "BOOKING ID", flex: 1, minWidth: 120 },
  {
    field: "status",
    headerName: "STATUS",
    flex: 1,
    minWidth: 130,
    renderCell: (params) => {
      const status = params.value;
      let bgColor = "#f1f5f9";
      let textColor = "#475569";

      if (status === "Confirmed") {
        bgColor = "#e8f5e9";
        textColor = "#2e7d32";
      } else if (status === "Pending") {
        bgColor = "#fff3e0";
        textColor = "#ed6c02";
      }

      return (
        <Chip
          label={status}
          size="small"
          sx={{
            fontWeight: "bold",
            borderRadius: "6px",
            fontSize: "0.7rem",
            backgroundColor: bgColor,
            color: textColor,
            border: `1px solid ${textColor}22`,
            height: "22px",
          }}
        />
      );
    },
  },
  { field: "scheduledDate", headerName: "DATE", flex: 1, minWidth: 120 },
  {
    field: "totalAmount",
    headerName: "AMOUNT",
    flex: 1,
    minWidth: 100,
    renderCell: (params) => (
      <Typography className="text-sm font-bold text-gray-900">
        ₹{params.value?.toLocaleString("en-IN")}
      </Typography>
    ),
  },
];

export default function BookingTable({
  rows = [],
  onlyShowCompleted = false,
  title,
  loading,
}) {
  // Logic: If onlyShowCompleted is true, force filter to 'Completed'
  const [filter, setFilter] = useState(onlyShowCompleted ? "Completed" : "All");

  // Update filter if the tab changes
  useEffect(() => {
    setFilter(onlyShowCompleted ? "Completed" : "All");
  }, [onlyShowCompleted]);

  const filteredRows = rows.filter((row) => {
    if (filter === "All") return true;
    return row.status === filter;
  });

  return (
    <Box className="w-full">
      <Paper
        elevation={0}
        variant="outlined"
        className="rounded-xl overflow-hidden bg-white shadow-sm"
      >
        {/* Filter Bar: Only show if we ARE NOT in "onlyShowCompleted" mode */}
        {!onlyShowCompleted && (
          <Box className="p-3 border-b border-gray-100 flex gap-2 bg-white">
            {["All", "Confirmed", "Pending"].map((type) => (
              <Chip
                key={type}
                label={type}
                onClick={() => setFilter(type)}
                size="small"
                variant={filter === type ? "filled" : "outlined"}
                sx={{
                  cursor: "pointer",
                  fontSize: "0.75rem",
                  backgroundColor: filter === type ? "#6366f1" : "transparent",
                  color: filter === type ? "#fff" : "inherit",
                  "&:hover": {
                    backgroundColor: filter === type ? "#4f46e5" : "#f8fafc",
                  },
                }}
              />
            ))}
          </Box>
        )}

        {/* Scrollable Container (Height 280px) */}
        <div style={{ height: 200, width: "100%" }}>
          <DataGrid
            rows={filteredRows}
            columns={columns}
            disableColumnMenu
            disableRowSelectionOnClick
            hideFooter
            sx={{
              border: "none",
              "& .MuiDataGrid-columnHeaders": { backgroundColor: "#fcfcfd" },
              "& .MuiDataGrid-cell": { borderBottom: "1px solid #f9f9f9" },
              "& .MuiDataGrid-virtualScroller": { overflowY: "auto" }, // Enables the scrollbar
            }}
          />
        </div>

        {/* Footer Summary */}
        <Box className="p-3 flex justify-end gap-6 border-t border-gray-100 bg-gray-50/50">
          <Typography className="text-gray-500 text-[10px] font-bold uppercase tracking-wider">
            Total:{" "}
            <span className="text-indigo-900 ml-1">
              ₹
              {filteredRows
                .reduce((sum, row) => sum + row.totalAmount, 0)
                .toLocaleString("en-IN")}
            </span>
          </Typography>
          <Typography className="text-gray-500 text-[10px] font-bold uppercase tracking-wider">
            Count:{" "}
            <span className="text-indigo-900 ml-1">{filteredRows.length}</span>
          </Typography>
        </Box>
      </Paper>
    </Box>
  );
}
