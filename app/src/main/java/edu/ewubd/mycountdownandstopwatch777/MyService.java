package edu.ewubd.mycountdownandstopwatch777;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;


public class MyService extends Service {
    final Timer timer = new Timer();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final Integer[] timeUpdate = {
                intent.getIntExtra("CountDown", 0),
                intent.getIntExtra("StopWatch", 0),
                intent.getIntExtra("CountDown_Service", 0),
                intent.getIntExtra("StopWatch_Service", 0),
        };

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Intent intentLocal = new Intent();
                intentLocal.setAction("Counter");
                if(timeUpdate[2] == 1){
                    timeUpdate[0]--;
                    if (timeUpdate[0]  <= 0) {
                        timer.cancel();

                    }
                    intentLocal.putExtra("TimeRemaining", timeUpdate[0]);
                }
                if(timeUpdate[3] == 1){
                    timeUpdate[1]++;
                    intentLocal.putExtra("TimeIncrsed", timeUpdate[1]);

                }
                sendBroadcast(intentLocal);

            }
        }, 0, 1000);
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        timer.cancel();
    }
}


