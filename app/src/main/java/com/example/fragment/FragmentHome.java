package com.example.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FragmentHome extends Fragment {
    TextView txtTemp, txtHumid;
    MQTTHelper mqttHelper;
    ToggleButton btnLED;
    boolean isSend = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_fragment_home, container, false);

        txtTemp = view.findViewById(R.id.txtTemperature);
        txtHumid = view.findViewById(R.id.txtHumidity);
        btnLED = view.findViewById(R.id.toggleButton);

        txtTemp.setText("40"+"°C");

        btnLED.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.d("mqtt", "Button is checked!");
                    sendDataMQTT("DAT_DO/f/bbc-led","1");
                }else{
                    Log.d("mqtt", "Button is unchecked!");
                    sendDataMQTT("DAT_DO/f/bbc-led", "0");
                }
            }
        });
        btnLED.setVisibility(View.VISIBLE);

        startMQTT();
//        setupScheduler();

        return view;
    }

    int waiting_period = 0;
    boolean send_message_again = false;
    List<MQTTMessage> list = new ArrayList<>();

    private void setupScheduler(){
        Timer aTimer = new Timer();
        TimerTask scheduler = new TimerTask() {
            @Override
            public void run() {
                Log.d("mqtt", "Timer is executed!");
                if(waiting_period > 0){
                    waiting_period--;
                    if(waiting_period == 0){
                        send_message_again = true;
                    }
                }
                if(send_message_again == true){
                    sendDataMQTT(list.get(0).topic, list.get(0).mess);
                    list.remove(0);
                }
                btnLED.setVisibility(View.VISIBLE);
            }
        };
        aTimer.schedule(scheduler, 5000, 1000);
    }

    private void sendDataMQTT(String topic, String value){
        waiting_period = 3;
        send_message_again = false;

        MQTTMessage aMessage = new MQTTMessage();
        aMessage.topic = topic;
        aMessage.mess = value;
        list.add(aMessage);

        MqttMessage msg = new MqttMessage();
        msg.setId(15234);
        msg.setQos(0);
        msg.setRetained(true);

        byte[] b = value.getBytes(Charset.forName("UTF-8"));
        msg.setPayload(b);
        try {
            mqttHelper.mqttAndroidClient.publish(topic, msg);
        }catch (MqttException e){

        }
    }

    private void startMQTT(){
        mqttHelper = new MQTTHelper(getActivity().getApplicationContext(), "9137");
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                Log.d("mqtt", "Connect is successfully!");
            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.d("mqtt", "Received: " + message.toString());
                if(topic.equals("DAT_DO/f/bbc-temp")){
                    txtTemp.setText("Temp: " + message.toString() + "°C");
                }

                if(topic.equals("DAT_DO/f/bbc-humid")){
                    txtHumid.setText("Humid: " + message.toString() + "%");
                }

                if(!isSend){
                    if(message.toString().equals("1")){
//                    if(Integer.valueOf(message.toString()) == 1){
                        btnLED.setChecked(true);
                    }else{
                        btnLED.setChecked(false);
                    }
                }
                isSend = false;

                if(topic.contains("abc") && message.toString().contains("123")){
                    waiting_period = 0;
                    send_message_again = false;
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }

    public class MQTTMessage{
        public String topic;
        public String mess;
    }

}
