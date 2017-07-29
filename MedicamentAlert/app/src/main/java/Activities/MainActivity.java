package Activities;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextClock;

import com.example.marcos.medicamentalert.R;
import com.example.marcos.medicamentalert.ReceptorAlarme;
import com.example.marcos.medicamentalert.TimePickerFragment;

import java.util.ArrayList;
import java.util.Calendar;

import models.Medicamento;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Medicamento> medicamentos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Spinner spinnerFrequencia = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterSpinnerFrequencia = ArrayAdapter.createFromResource(this,
                R.array.frequencia_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterSpinnerFrequencia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerFrequencia.setAdapter(adapterSpinnerFrequencia);

        Spinner spinnerDosagem = (Spinner) findViewById(R.id.spinnerDosagem);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterSpinnerDosagem = ArrayAdapter.createFromResource(this,
                R.array.dosagem_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterSpinnerDosagem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerDosagem.setAdapter(adapterSpinnerDosagem);

        Button salvar = (Button) findViewById(R.id.salvar);
        salvar.setOnClickListener(salvarOnClickListener);

        TextClock textClock = (TextClock) findViewById(R.id.textClock);
        textClock.setOnClickListener(escolheHorarioOnClickListener);

    }

    private View.OnClickListener escolheHorarioOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TimePickerFragment dialogFragment = new TimePickerFragment();
            dialogFragment.show(getFragmentManager(), "TimePicker");
        }
    };

    private View.OnClickListener salvarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Boolean camposPreenchidos = true;
            EditText viewNomeMedicamento = (EditText) findViewById(R.id.form_nomeMedicamento);
            if( viewNomeMedicamento.getText().toString().length() == 0 ) {
                camposPreenchidos = false;
                viewNomeMedicamento.setError("Por favor, digite o nome do medicamento!");
            }
            //EditText viewNomeMedico = (EditText) findViewById(R.id.form_nomeMedico);
            EditText viewDosagem = (EditText) findViewById(R.id.form_quantidadeDosagem);
            if( viewDosagem.getText().toString().length() == 0 ) {
                camposPreenchidos = false;
                viewDosagem.setError("Por favor, digite a dosagem!");
            }

            if (camposPreenchidos) {
                Spinner spinnerDosagem = (Spinner) findViewById(R.id.spinnerDosagem);
                String nomeMedicamento = viewNomeMedicamento.getText().toString();
                //String nomeMedico = viewNomeMedico.getText().toString();
                String dosagem = viewDosagem.getText().toString();
                String dosagemMetrica = spinnerDosagem.getSelectedItem().toString();

                Medicamento medicamento = new Medicamento(nomeMedicamento, Integer.valueOf(dosagem), dosagemMetrica);
                medicamentos.add(medicamento);

                TextClock textClock = (TextClock) findViewById(R.id.textClock);

                AlarmManager gerenciador = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Calendar cal = Calendar.getInstance();

                String stringHour = textClock.getText().toString();
                cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(stringHour.substring(0, 2)));
                cal.set(Calendar.MINUTE, Integer.valueOf(stringHour.substring(3)));
                //cal.set(Calendar.SECOND, 0);
                //cal.set(Calendar.MILLISECOND, 0);

                if (cal.getTimeInMillis() < System.currentTimeMillis()) {
                    cal.add(Calendar.DAY_OF_YEAR, 1);
                }

                gerenciador.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, obterIntentPendente(MainActivity.this));

            }
        }
    };

    private static PendingIntent obterIntentPendente(Context contexto) {

        Intent i = new Intent(contexto, ReceptorAlarme.class);
        i.putExtra("Ringtone",
                Uri.parse("getResources().getResourceName(R.raw.shankh_final_mid)"));
        return PendingIntent.getBroadcast(contexto, 0, i, 0);
    }

}
