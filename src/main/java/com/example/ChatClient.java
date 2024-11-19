package com.example;

import java.net.URI;
import java.util.Scanner;
import javax.websocket.*;

@ClientEndpoint
public class ChatClient {
	private Session session;

	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		System.out.println("Connected to server");
	}

	@OnMessage
	public void onMessage(String message) {
		System.out.println(message);
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		System.out.println("Disconnected from server");
	}

	public void sendMessage(String message) {
		try {
			session.getBasicRemote().sendText(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			ChatClient client = new ChatClient();
			container.connectToServer(client, new URI("ws://localhost:8025/websocket/chat"));

			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter messages (type 'exit' to quit):");

			String message;
			while (!(message = scanner.nextLine()).equalsIgnoreCase("exit")) {
				client.sendMessage(message);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
