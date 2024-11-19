package com.example;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/chat")
public class ChatServer {
	private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());

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

