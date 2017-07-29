package com.example.marcos.medicamentalert;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import Activities.AlarmeActivity;


/**
 * Created by Marcos on 17/07/2017.
 */

public class ReceptorAlarme extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, AlarmeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.getParcelableExtra("Ringtone");
        context.startActivity(i);
    }

}