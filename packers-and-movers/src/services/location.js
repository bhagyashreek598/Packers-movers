import axios from "axios";
import { BASE_URL } from "./config";

// GET STATES
export async function getStates() {
  const response = await axios.get(`${BASE_URL}/users/locations/states`);
  return response.data;
}

// GET CITIES BY STATE NAME
export async function getCitiesByState(stateName) {
  const response = await axios.get(
    `${BASE_URL}/users/locations/states/${encodeURIComponent(
      stateName,
    )}/cities`,
  );
  return response.data;
}
