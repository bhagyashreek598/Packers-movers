import { Dialog, DialogTitle, DialogContent, IconButton } from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";
import BookAMoveForm from "../BookAMoveForm";

const BookMoveDialog = ({ open, onClose }) => {
  return (
    <Dialog open={open} onClose={onClose} fullWidth maxWidth="sm">
      <DialogTitle className="flex justify-between items-center">
        <div className="flex items-center pl-2.5">
          <img
            src="images/logo.png"
            alt="Courier"
            style={{
              height: "50px",
              objectFit: "contain",
              objectPosition: "bottom right",
            }}
          />
          <span>
            <strong className="pl-2.5">Book a Move</strong>
          </span>
        </div>
        <IconButton onClick={onClose}>
          <CloseIcon />
        </IconButton>
      </DialogTitle>

      {/* ===== Content ===== */}
      <DialogContent dividers className="space-y-4">
        <BookAMoveForm onClose={onClose} />
      </DialogContent>
    </Dialog>
  );
};

export default BookMoveDialog;
