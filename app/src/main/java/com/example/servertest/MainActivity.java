package com.example.servertest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

//import
public class MainActivity extends AppCompatActivity {
    private server server;
    private server reader;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        this.server = new server();
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reader = new server();
        Thread reader_thread = new Thread(reader);
        reader_thread.start();
        TextInputEditText message = findViewById(R.id.InputMessage);

        LinearLayout message_layout = findViewById(R.id.linearLayout);
        ScrollView scrollArea = findViewById(R.id.scrollArea);
        LinearLayout present_message = findViewById(R.id.present_message);

        Button sendButton = findViewById(R.id.send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_message_to_contact(message.getText().toString(), present_message);
                message.setText("");
            }
        });



    }
    protected void send_message_to_contact(CharSequence messageText, @NonNull LinearLayout present_message){
        System.out.println(messageText);
        TextView sended_mesaage = new TextView(getApplicationContext());
        sended_mesaage.setTextAppearance(this, R.style.message);

        sended_mesaage.setText(messageText);

        sended_mesaage.setBackgroundColor(getResources().getColor(R.color.black));
        sended_mesaage.setBackground(getApplicationContext().getDrawable(R.drawable.shape));
        present_message.addView(sended_mesaage);

        this.reader.send_message((String) messageText);
    }
}