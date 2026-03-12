import axios from "axios";
import { BASE_URL } from "./config";

// CUSTOMER SIGNUP
export async function signupCustomer(data) {
  alert("signup api");
  const response = await axios.post(`${BASE_URL}/users/signup/customer`, data);
  return response.data;
}

// LOGIN
export async function signinUser(credentials) {
  try {
    const response = await axios.post(`${BASE_URL}/users/signin`, credentials);
    return response.data;
  } catch (e) {
    if (error.response.status === 403) {
      alert(
        "Access Forbidden (403): Invalid credentials or insufficient permissions.",
      );
    } else {
      alert("Server is currently down. Please try again later.");
    }
  }
}
