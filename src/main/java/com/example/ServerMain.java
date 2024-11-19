package com.example;

import org.glassfish.tyrus.server.Server;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ServerMain {
	public static void main(String[] args) {

		try { 
			Server server = new Server("localhost", 8025, "/websockets", null, ServerConfig.class, ChatServer.class);
			server.start();
			System.out.println("Chat server started on ws://localhost:8025/websocket/chat");
			System.out.println("Press enter to stop the server...");

			new BufferedReader(new InputStreamReader(System.in)).readLine();

			server.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
