package me.yorick.mqtt.client.service;

import javax.annotation.PostConstruct;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import me.yorick.mqtt.client.config.MqttClientManager;

@Profile("subscriber")
@Service
@Slf4j
public class MqttSubscriber {

	@Autowired
	private MqttClientManager mqttManager;
	
	@PostConstruct
	public void init() {
		try {
			log.info("connecting");
			mqttManager.setMessageListener(new MessageListener());
			mqttManager.connect();
			
			
		} catch (MqttException e) {
			log.error("failed to subscribe ",e);
		}
	}
	
	private class MessageListener implements IMqttMessageListener{

		@Override
		public void messageArrived(String topic, MqttMessage message) throws Exception {
			
			log.info("receive {}", new String(message.getPayload()));
		}
		
	}
}
