// src/services/booking.service.js
import axios from "axios";
import { BASE_URL, getAuthHeader } from "./config";

// CREATE BOOKING
export async function createBooking(data) {
  console.log("booking payload: ", data);
  alert("reached booking api");
  console.log("reached booking api");
  const response = await axios.post(`${BASE_URL}/users/bookings`, data, {
    headers: getAuthHeader(),
  });
  return response.data;
}

// REQUEST CANCELLATION (request only)
export async function requestBookingCancellation(bookingId, reason) {
  const response = await axios.post(
    `${BASE_URL}/users/bookings/${bookingId}/request-cancellation`,
    reason,
    { headers: getAuthHeader() },
  );
  return response.data;
}

// CANCEL BOOKING (final cancel)
export async function cancelBooking(bookingId, reason) {
  const response = await axios.put(
    `${BASE_URL}/users/bookings/${bookingId}/cancel`,
    reason,
    { headers: getAuthHeader() },
  );
  return response.data;
}

// GET CURRENT BOOKINGS
export async function getCurrentBookings() {
  // alert("getCurrentBokking");
  const response = await axios.get(`${BASE_URL}/users/bookings/current`, {
    headers: getAuthHeader(),
  });
  console.log("first");
  console.log("getCurrentBookings: ", response);
  return response.data;
}

// GET BOOKING HISTORY
export async function getBookingHistory() {
  const response = await axios.get(`${BASE_URL}/users/bookings/history`, {
    headers: getAuthHeader(),
  });
  console.log("sec");
  console.log("getBookingHistory: ", response);
  return response.data;
}

// UPDATE BOOKING BY ID
export async function updateBooking(bookingId, data) {
  const response = await axios.put(
    `${BASE_URL}/users/bookings/${bookingId}`,
    data,
    { headers: getAuthHeader() },
  );
  return response.data;
}

// UPDATE USER PROFILE
export async function updateUserProfile(profile) {
  const response = await axios.put(`${BASE_URL}/users/profile`, profile, {
    headers: getAuthHeader(),
  });
  return response.data;
}
