package com.example.marcos.medicamentalert.activities;

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
import android.widget.TextView;

import com.example.marcos.medicamentalert.R;
import com.example.marcos.medicamentalert.bancoDados.Banco;
import com.example.marcos.medicamentalert.fragments.ReceptorAlarme;
import com.example.marcos.medicamentalert.models.Medicamento;

import java.util.List;

public class AlarmeActivity extends Activity {
    public static Banco bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.caixa_alarme);

        Button botaoTomar = (Button) findViewById(R.id.botaoTomar);
        botaoTomar.setOnClickListener(tomarOnClickListener);
        bd = new Banco(this);
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel(1);
        String medicamento = getIntent().getStringExtra("nomeMedicamento");
        Medicamento medicamentoAtualizado = null;
        List<Medicamento> medicamentos= bd.getMedicamentosNoBanco();
        for(int i = 0; i<medicamentos.size(); i++){
            if(medicamentos.get(i).getNome().equals(medicamento)){
                medicamentoAtualizado = medicamentos.get(i);
                medicamentoAtualizado.setSituacaoTomado("sim");
            }
        }
        bd.atualizaMedicamento(medicamentoAtualizado);
        ((TextView)findViewById(R.id.nomeMedicamentoCaixaAlarme)).setText(medicamento);
        String dosagem = "" + getIntent().getFloatExtra("dosagem", 0) + getIntent().getStringExtra("metricaDosagem");
        ((TextView)findViewById(R.id.dosagemCaixaAlarme)).setText(dosagem);
    }

    private View.OnClickListener tomarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String horario = getIntent().getStringExtra("horario");
            int id = getIntent().getIntExtra("id", 0);
            int count = getIntent().getIntExtra("count", 0);
            Log.i("Recebido código:", String.valueOf(id+count));
            Log.i("Recebido id:", String.valueOf(id));
            Log.i("Recebido count:", String.valueOf(count));
            listaMedicamentosActivity.medicamentoTomado(horario, id);
            Intent i = new Intent(AlarmeActivity.this, ReceptorAlarme.class);
            PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), count + id, i, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.cancel(pi);
            ReceptorAlarme.stopRingtone();
            Log.i("Alarme", "Alarme cancelado!");
            finish();
        }
    };


}