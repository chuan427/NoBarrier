package com;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
@EnableWebSocket
public class WSConfig {
	
	@Bean
	public ServerEndpointExporter serverPoint() {
		return new ServerEndpointExporter();
	}
	
	
}
