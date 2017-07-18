package com.example.marcos.medicamentalert;

/**
 * Created by Marcos on 17/07/2017.
 */

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
            Intent i = new Intent(getApplicationContext(), Ringtone.class);
            stopService(i);
        }
    };


}