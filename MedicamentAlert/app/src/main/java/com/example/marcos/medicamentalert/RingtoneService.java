package com.example.marcos.medicamentalert;

import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Marcos on 18/07/2017.
 */

public class RingtoneService extends Service{
    static Ringtone ringtone;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        //activating alarm sound
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(getBaseContext(), notification);
        //playing sound alarm
        ringtone.play();

        return START_NOT_STICKY;
    }
    @Override
    public void onDestroy()
    {
        ringtone.stop();
    }

}
