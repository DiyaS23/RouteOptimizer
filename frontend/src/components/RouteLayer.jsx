import { Polyline } from "react-leaflet";

export default function RouteLayer({ coordinates }) {
  if (!coordinates || coordinates.length === 0) return null;
  return <Polyline positions={coordinates} color="blue" />;
}
