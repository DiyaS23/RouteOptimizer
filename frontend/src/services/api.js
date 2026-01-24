import axios from "axios";

export const calculateRoute = async (source, target) => {
  const res = await axios.post(
    `http://localhost:8080/routes/calculate?source=${source}&target=${target}`
  );
  return res.data;
};
