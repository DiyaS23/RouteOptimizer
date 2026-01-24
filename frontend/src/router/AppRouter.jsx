import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import RiderView from "../pages/RiderView";

export default function AppRouter() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/rider/test-rider" />} />
        <Route path="/rider/:id" element={<RiderView />} />
      </Routes>
    </BrowserRouter>
  );
}
