package com.example.marcos.medicamentalert;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * Created by Marcos on 17/07/2017.
 */

public class ReceptorAlarme extends BroadcastReceiver {
    static Ringtone ringtone;
    @Override
    public void onReceive(Context context, Intent intent) {
        //String horario = intent.getStringExtra("horario");
        //int id = intent.getIntExtra("id", 0);
        Log.i("Alarme", "Alarme recebido");
        Intent i = new Intent(context, AlarmeActivity.class);
        i.putExtras(intent);
        //i.putExtra("horario", horario);
       // i.putExtra("id", id);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        geraNotificacao(context);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(context, uri);
        ringtone.play();
        context.startActivity(i);
    }
    public static void stopRingtone(){
        if (ringtone != null){
            ringtone.stop();
        }
    }
    private void geraNotificacao(Context context){
        NotificationManager nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        PendingIntent pi = PendingIntent.getActivity(context, 0, new Intent(context, AlarmeActivity.class), 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setTicker("Hora do medicamento!");
        builder.setContentTitle("Medicament Alarm");
        builder.setContentText("Texto");
        builder.setSmallIcon(android.R.drawable.ic_media_play);
        builder.setContentIntent(pi);

        Notification notification = builder.build();
        notification.vibrate = new long[]{150, 300, 150, 600};
        nm.notify(1, notification);
        Log.i("Notificação", "Notificação gerada!");
    }


}