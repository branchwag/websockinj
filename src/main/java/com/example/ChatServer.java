package com.example;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/chat")
public class ChatServer {
	private static final Set<Session> sessions = new CopyOnWriteArraySet<>();

	@OnOpen
	public void onOpen(Session session) {
		sessions.add(session);
		broadcast("User " + session.getId() + " joined the chat");
		System.out.println("New session: " + session.getId());
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		broadcast("User " + session.getId() + ": " + message);
	}

	@OnClose
	public void onClose(Session session) {
		sessions.remove(session);
		broadcast("User " + session.getId() + " left the chat");
		System.out.println("Session closed: " + session.getId());
	}

	@OnError
	public void onError(Throwable error) {
		error.printStackTrace();
	}


	private void broadcast (String message) {
		synchronized (sessions) {
			for (Session session : sessions ) {
				try {
					session.getBasicRemote().sendText(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

