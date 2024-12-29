package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import android.util.Log;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private EditText tv;
    private TextView info_block;
    private  Scanner sc = new Scanner(System.in);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn = findViewById(R.id.button);
        tv = findViewById(R.id.editText);
        info_block = findViewById(R.id.textView);
        btn.setOnClickListener(v ->{
            String country = tv.getText().toString();
            String www = "https://restcountries.com/v3.1/name/"+country.toLowerCase();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpsURLConnection connection;
                    URL url = null;
                    try {
                        url = new URL(www);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        connection = (HttpsURLConnection) url.openConnection();
                        connection.setConnectTimeout(10000);
                        connection.connect();
                        sc = new Scanner(connection.getInputStream());
                        String result = sc.nextLine();
                        info_block.setText(result.replace(",", "").replace("}","\n").replace("{","\n").replace("[","").replace("]","").replace('"', ' '));
                    } catch (IOException e) { // Input Output
                        info_block.setText("Что-то случилось, проверь связь или название страны");
                    }
                }
            }).start();
            }
        );
    }
}


