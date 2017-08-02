package com.example.marcos.medicamentalert;

/**
 * Created by Marcos on 17/07/2017.
 */

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.marcos.medicamentalert.R;

public class AlarmeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.caixa_alarme);

        Button botaoTomar = (Button) findViewById(R.id.botaoTomar);
        botaoTomar.setOnClickListener(tomarOnClickListener);

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel(1);
    }

    private View.OnClickListener tomarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(AlarmeActivity.this, ReceptorAlarme.class);
            PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, i, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.cancel(pi);
            ReceptorAlarme.stopRingtone();
            Log.i("Alarme", "Alarme cancelado!");
            finish();
        }
    };


}