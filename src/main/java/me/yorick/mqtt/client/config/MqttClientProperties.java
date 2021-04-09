package me.yorick.mqtt.client.config;

import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "mqtt")
@Data
public class MqttClientProperties {
	
	private String userName;
	
	private String password=Strings.EMPTY;
	
	private String[] servers;

	private String clientId;
}
