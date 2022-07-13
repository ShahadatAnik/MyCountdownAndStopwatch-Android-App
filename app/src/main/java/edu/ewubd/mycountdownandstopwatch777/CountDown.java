package edu.ewubd.mycountdownandstopwatch777;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

import static android.Manifest.permission.FOREGROUND_SERVICE;

public class CountDown extends AppCompatActivity {

    private TextView Timer;
    private Button Start, Reset, Pause, BELL;
    private int start = 50;
    private int reset = 50;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countdown);
        ActivityCompat.requestPermissions(this, new String[]{FOREGROUND_SERVICE}, PackageManager.PERMISSION_GRANTED);
        Timer = findViewById(R.id.tv_countdown);
        Start = findViewById(R.id.btn_start);
        Reset = findViewById(R.id.btn_reset);
        Pause = findViewById(R.id.btn_pause);
        BELL = findViewById(R.id.btn_bell_stop);

        BELL.setVisibility(View.GONE);

        Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), alarm);


        Start.setOnClickListener(v->countdownUpdate(true, false, false, mp));
        Reset.setOnClickListener(v->countdownUpdate(false, true, false, mp));
        Pause.setOnClickListener(v->countdownUpdate(false, false, true, mp));

        findViewById(R.id.btn_exit_countdown).setOnClickListener(v->finish());

        BELL.setOnClickListener(v->{mp.stop(); mp.reset();});

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("Counter");
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Integer integerTime = intent.getIntExtra("TimeRemaining", 0);
                start = integerTime;
                Timer.setText(integerTime.toString());

                System.out.println("Timer: " + integerTime);

                if(integerTime <= 0) {
                    mp.start();
                    BELL.setVisibility(View.VISIBLE);
                }

            }
        };
        registerReceiver(broadcastReceiver, intentFilter);
    }

    public void countdownUpdate(Boolean isStart, Boolean isReset, Boolean isPause, MediaPlayer mp){
        Intent intentService = new Intent(this, MyService.class);
        if(isStart){
            intentService.putExtra("CountDown", start);
            intentService.putExtra("CountDown_Service", 1);
            startService(intentService);
        }
        else if(isReset){
            intentService.putExtra("CountDown", reset);
            intentService.putExtra("CountDown_Service", 1);
            start = reset;
            Integer integerTime = reset;
            Timer.setText(integerTime.toString());
            BELL.setVisibility(View.GONE);
            mp.stop();
            mp.reset();
            stopService(intentService);
        }else if(isPause){
            intentService.putExtra("CountDown_Service", 1);
            stopService(intentService);
        }

    }

}