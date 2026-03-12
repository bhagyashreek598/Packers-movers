export const BASE_URL = "http://localhost:8080";
// export const BASE_URL =  "https://packers-movers-backend-production.up.railway.app";

export function getAuthHeader() {
  const token = sessionStorage.getItem("token");
  return {
    Authorization: `Bearer ${token}`,
  };
}
