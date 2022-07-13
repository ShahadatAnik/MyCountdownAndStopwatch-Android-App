package edu.ewubd.mycountdownandstopwatch777;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

import static android.Manifest.permission.FOREGROUND_SERVICE;

public class StopWatch extends AppCompatActivity {

    private TextView Timer;
    private Button Start, Reset, Pause;
    private int start = 0;
    private int reset = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stopwatch);
        ActivityCompat.requestPermissions(this, new String[]{FOREGROUND_SERVICE}, PackageManager.PERMISSION_GRANTED);
        Timer = findViewById(R.id.tv_stopwatch);
        Start = findViewById(R.id.btn_start_stopwatch);
        Reset = findViewById(R.id.btn_reset_stopwatch);
        Pause = findViewById(R.id.btn_pause_stopwatch);

        Start.setOnClickListener(v->stopwatchUpdate(true, false, false));
        Reset.setOnClickListener(v->stopwatchUpdate(false, true, false));
        Pause.setOnClickListener(v->stopwatchUpdate(false, false, true));

        findViewById(R.id.btn_exit_stopwatch).setOnClickListener(v->finish());

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("Counter");
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Integer integerTime = intent.getIntExtra("TimeIncrsed", 0);
                start = integerTime;
                Timer.setText(integerTime.toString());
            }
        };
        registerReceiver(broadcastReceiver, intentFilter);
    }

    public void stopwatchUpdate(Boolean isStart, Boolean isReset, Boolean isPause){
        Intent intentService = new Intent(this, MyService.class);
        if(isStart){
            intentService.putExtra("StopWatch", start);
            intentService.putExtra("StopWatch_Service", 1);
            startService(intentService);
        }
        else if(isReset){
            intentService.putExtra("StopWatch", reset);
            intentService.putExtra("StopWatch_Service", 1);
            start = reset;
            Integer integerTime = reset;
            Timer.setText(integerTime.toString());
            stopService(intentService);
        }else if(isPause){
            stopService(intentService);
        }

    }

}