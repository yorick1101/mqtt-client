package me.yorick.mqtt.client.service;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import me.yorick.mqtt.client.config.MqttClientManager;

@Profile("publisher")
@Service
@EnableScheduling
@Slf4j
public class MqttPublisher {

	@Autowired
	private MqttClientManager mqttClient;

	@PostConstruct
	public void init() throws MqttException {
		mqttClient.connect();
	}

	@Scheduled(cron = "*/10 * * * * ?")
	public void publish() {
		if(mqttClient.getClient().isConnected()) {
			MqttTopic topic = mqttClient.getClient().getTopic(mqttClient.getTopic());
			String msg = LocalDateTime.now().toString();
			try {
				MqttDeliveryToken token = topic.publish(msg.getBytes(), 1, false);
				log.info("push {},{}", mqttClient.getClient().isConnected(), token.getMessageId());
			} catch (MqttException e) {
				log.error("publish failed", e);
			}
		}
	}

}
