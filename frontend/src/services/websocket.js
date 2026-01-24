import { Client } from "@stomp/stompjs";

export const connectWS = (onMessage, onConnected) => {
  const client = new Client({
    brokerURL: "ws://localhost:8080/ws",
    reconnectDelay: 5000,

    onConnect: () => {
      onConnected();

      client.subscribe("/topic/routes/test-rider", (msg) => {
        onMessage(JSON.parse(msg.body));
      });
    },

    onWebSocketError: (err) => {
      alert("WebSocket error");
      console.error(err);
    },

    onStompError: (frame) => {
      alert("STOMP error: " + frame.body);
    },
  });

  client.activate();
};
