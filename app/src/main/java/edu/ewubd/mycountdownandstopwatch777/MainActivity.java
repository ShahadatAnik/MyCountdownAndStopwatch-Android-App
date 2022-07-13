package edu.ewubd.mycountdownandstopwatch777;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button countdown_go, stopwatch_go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countdown_go = findViewById(R.id.btn_countdown_go);
        stopwatch_go = findViewById(R.id.btn_stopwatch_go);

        countdown_go.setOnClickListener(v -> {
            Intent intent_CountDown = new Intent(this, CountDown.class);
            startActivity(intent_CountDown);
        });
        stopwatch_go.setOnClickListener(v -> {
            Intent intent_StopWatch = new Intent(this, StopWatch.class);
            startActivity(intent_StopWatch);
        });

        findViewById(R.id.btn_exit).setOnClickListener(v->finish());
    }

    public void onDestroy() {
        super.onDestroy();
    }
}