package a7amdon.enis.tn.testiot.mqtt;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import a7amdon.enis.tn.testiot.MainActivity;

/**
 * Created by 7amdon on 11/12/2016.
 */

public class MQTTPublish {
    static String MQTTHOST="tcp://m13.cloudmqtt.com:18400";
    static String USERNAME="aa";
    static String PASSWORD="aa";
    static String TOPIC="iot";
    MqttAndroidClient client;

    public  MQTTPublish(final Context context){
        String clientId = MqttClient.generateClientId();
        client =
                new MqttAndroidClient(context, MQTTHOST,
                        clientId);

        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());

        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(context, "Connected !", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(context, "Connection failed !", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public void publish(View v,String message){
        String topic = TOPIC;
        byte[] encodedPayload = new byte[0];
        try {
            /*encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);*/
            client.publish(topic, message.getBytes(),0,false);
        } catch (/*UnsupportedEncodingException |*/ MqttException e) {
            e.printStackTrace();
        }
    }
}
