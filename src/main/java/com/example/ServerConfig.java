package com.example;

import java.util.HashSet;
import java.util.Set;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;

public class ServerConfig implements ServerApplicationConfig {
	@Override
	public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> endpointClasses) {
		Set<ServerEndpointConfig> configs = new HashSet<>();

		ServerEndpointConfig config = ServerEndpointConfig.Builder
			.create(ChatServer.class, "/chat")
			.build();

		configs.add(config);
		return configs;
	}

	@Override
	public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> scanned) {
		Set<Class<?>> results = new HashSet<>();
		results.add(ChatServer.class);
		return results;
	}
}
