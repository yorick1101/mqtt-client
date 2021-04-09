package me.yorick.mqtt.client.config;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MqttClientCallback implements MqttCallback{
	
	@Autowired
	private MqttClientManager client;
	
	
	private int count = 0;
	
	@Override
	public void connectionLost(Throwable e) {
		log.warn("mqtt server connection lost {}",count++, e);
		try {
			client.connect();
			
			//Should also re-subscribe to topics is necessary 
			
		} catch (MqttException e1) {
			log.error("reconnect failed", e1);
		}
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		log.info("messageArrived in callback");
		
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
	
		log.info("deliveryComplete in callback");
	}

}
