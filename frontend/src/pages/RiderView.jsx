import { useEffect, useState } from "react";
import MapView from "../components/MapView";
import RouteLayer from "../components/RouteLayer";
import { connectWS } from "../services/websocket";

export default function RiderView() {
  const [status, setStatus] = useState("🔴 Connecting to WebSocket...");
  const [route, setRoute] = useState([]);

  useEffect(() => {
    connectWS(
      (data) => {
        setStatus("🚦 TRAFFIC UPDATE RECEIVED");
        if (data.coordinates) {
          setRoute(data.coordinates);
        }
      },
      () => {
        setStatus("🟢 WebSocket CONNECTED");
      }
    );
  }, []);

  return (
    <>
      <div
        style={{
          padding: "12px",
          background: "#111",
          color: "#0f0",
          fontWeight: "bold",
          textAlign: "center",
        }}
      >
        {status}
      </div>

      <MapView>
        <RouteLayer coordinates={route} />
      </MapView>
    </>
  );
}
