import Swal from "sweetalert2";

/* CONFIRM ALERT (Delete / Yes-No) */
export const confirmAlert = async ({
  title = "Are you sure?",
  text = "This action cannot be undone",
  confirmText = "Yes",
  cancelText = "Cancel",
  icon = "warning",
}) => {
  return Swal.fire({
    title,
    text,
    icon,
    showCancelButton: true,
    confirmButtonText: confirmText,
    cancelButtonText: cancelText,
    reverseButtons: true,
  });
};

/*  SUCCESS ALERT */
export const successAlert = ({ title = "Success", text = "" }) => {
  return Swal.fire({
    icon: "success",
    title,
    text,
  });
};

/*  ERROR ALERT */
export const errorAlert = ({
  title = "Oops...",
  text = "Something went wrong!",
}) => {
  return Swal.fire({
    icon: "error",
    title,
    text,
  });
};

/* INFO ALERT */
export const infoAlert = ({ title = "Info", text = "" }) => {
  return Swal.fire({
    icon: "info",
    title,
    text,
  });
};

/* INPUT ALERT (Reusable) */
export const inputAlert = async ({
  title = "Enter value",
  placeholder = "",
  confirmText = "Submit",
}) => {
  return Swal.fire({
    title,
    input: "text",
    inputPlaceholder: placeholder,
    showCancelButton: true,
    confirmButtonText: confirmText,
  });
};
/* TYPE TO CONFIRM DELETE */
export const typeToConfirmDelete = async ({
  title = "Confirm Deletion",
  text = "Type DELETE to confirm this action",
  confirmText = "Delete",
  requiredText = "DELETE",
}) => {
  return Swal.fire({
    title,
    text,
    icon: "warning",
    input: "text",
    inputPlaceholder: `Type ${requiredText}`,
    showCancelButton: true,
    confirmButtonText: confirmText,
    preConfirm: (value) => {
      if (value !== requiredText) {
        Swal.showValidationMessage(
          `You must type "${requiredText}" to continue`,
        );
        return false;
      }
      return true;
    },
  });
};
