package me.yorick.mqtt.client.config;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MqttClientManager{
	private MqttConnectOptions options;
	private MqttClient client;
	
	private String topic;
	private IMqttMessageListener messageListener;
	
	public void connect() throws MqttException {
		client.connectWithResult(options);
		
		if(topic!=null && messageListener!=null) {
			client.subscribe(topic, messageListener);
		}
	}
	
	
	
	
}
