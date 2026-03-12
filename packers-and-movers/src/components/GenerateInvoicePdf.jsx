import jsPDF from "jspdf";

const generateInvoicePDF = ({ formData, invoice }) => {
  if (!invoice) return;

  const doc = new jsPDF();

  doc.setFontSize(16);
  doc.text("Packers & Movers Pvt. Ltd.", 20, 20);

  doc.setFontSize(11);
  doc.text("Estimated Invoice", 20, 28);

  doc.line(20, 32, 190, 32);

  // Pickup
  doc.text("Pickup:", 20, 42);
  doc.text(
    `${formData.pickupLabel}
${formData.pickupCity}, ${formData.pickupState}`,
    20,
    50
  );

  // Drop
  doc.text("Drop:", 120, 42);
  doc.text(
    `${formData.dropLabel}
${formData.dropCity}, ${formData.dropState}`,
    120,
    50
  );

  doc.line(20, 70, 190, 70);

  doc.text(`Service Type: ${formData.serviceCategory}`, 20, 82);
  doc.text(`Assigned Vehicle: ${invoice.vehicle}`, 20, 90);
  doc.text(`Distance: ${invoice.distance} km`, 20, 98);

  doc.line(20, 104, 190, 104);

  doc.text(`Base Service Cost: ₹ ${invoice.base}`, 20, 116);
  doc.text(`Handling Charges: ₹ ${invoice.handling}`, 20, 124);

  doc.line(20, 132, 190, 132);

  doc.setFontSize(13);
  doc.text(`Total Amount: ₹ ${invoice.total}`, 20, 146);

  doc.save("moving-invoice.pdf");
};

export default generateInvoicePDF;
