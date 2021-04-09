package me.yorick.mqtt.client.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class MqttClientConfig {

	@Autowired
	private Environment env;
	
    @Autowired
    private MqttClientProperties properties;
    
    @Autowired
    private MqttClientCallback callback;
	
	
	private MqttConnectOptions getMqttConnectOptions() {
		
		MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setCleanSession(true);
        mqttConnectOptions.setConnectionTimeout(30); //default is 30
       
        /*
        *  the maximum time interval between messages sent or received.
        *  The client will ensure that at least one messagetravels across the network 
        *  within each keep alive period. In the absence of adata-related message during the time period, 
        *  the client sends a very small"ping" message, which the server will acknowledge. 
        *  
        *  A value of 0 disableskeepalive processing in the client. 
        */
        mqttConnectOptions.setKeepAliveInterval(60); 
        /*
         * If set to true, in the event that the connection is lost, 
         * the client will attempt to reconnect to the server. 
         * 
         * It will initially wait 1 second before it attempts to reconnect, 
         * for every failed reconnect attempt, the delay will double until 
         * it is at 2 minutes at which point the delay will stay at 2minutes.
         * 
         */
        mqttConnectOptions.setAutomaticReconnect(true);
        
        mqttConnectOptions.setUserName(properties.getUserName());
        mqttConnectOptions.setPassword(properties.getPassword().toCharArray());
        mqttConnectOptions.setServerURIs(properties.getServers());
        
        return mqttConnectOptions;
	}
	
	@Bean
    public MqttClientManager newMqttClientManager() throws MqttException {
		
		MqttClient client = new MqttClient(properties.getServers()[0], properties.getClientId()+"-"+env.getActiveProfiles()[0]);
		client.setCallback(callback);
		
		return MqttClientManager.builder().client(client).options(getMqttConnectOptions()).topic("wahaha/topic1").build();
		
    } 
	
}
