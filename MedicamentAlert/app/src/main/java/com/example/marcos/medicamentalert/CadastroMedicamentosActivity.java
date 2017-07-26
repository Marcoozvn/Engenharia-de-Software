package com.example.marcos.medicamentalert;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextClock;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class CadastroMedicamentosActivity extends AppCompatActivity {
    private ArrayList<Medicamento> medicamentos = new ArrayList<>();
    private int ultimoTextClock = R.id.textClock;
    private int numTextClocks = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_medicamentos);
        /*
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        Spinner spinnerFrequencia = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterSpinnerFrequencia = ArrayAdapter.createFromResource(this,
                R.array.frequencia_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterSpinnerFrequencia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerFrequencia.setAdapter(adapterSpinnerFrequencia);
        */
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

        Button adicionaHorario = (Button) findViewById(R.id.botaoAdicionaHorario);
        adicionaHorario.setOnClickListener(adicionaHorarioOnClickListener);
        TextClock textClock = (TextClock) findViewById(R.id.textClock);
        textClock.setOnClickListener(escolheHorarioOnClickListener);

    }

    
    // ARRUMAR ISSO AQUI! (Tamanho, fonte, padding, posição do botão)
    private View.OnClickListener adicionaHorarioOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout3);
            TextClock textClockaux = new TextClock(getApplicationContext());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.BELOW, ultimoTextClock);
            textClockaux.setLayoutParams(layoutParams);
            switch (numTextClocks){
                case 1:
                    textClockaux.setId(R.id.textClock2);
                    break;
                case 2:
                    textClockaux.setId(R.id.textClock3);
                    break;
                case 3:
                    textClockaux.setId(R.id.textClock4);
                    break;
                case 5:
                    textClockaux.setId(R.id.textClock5);
                    break;
                case 6:
                    textClockaux.setId(R.id.textClock6);
                    break;
                case 7:
                    textClockaux.setId(R.id.textClock7);
                    break;
            }

            textClockaux.setTextColor(getResources().getColor(R.color.colorPrimary));
            relativeLayout.addView(textClockaux);
            textClockaux.setOnClickListener(escolheHorarioOnClickListener);
            ultimoTextClock = textClockaux.getId();
            numTextClocks++;
        }
    };

    private View.OnClickListener escolheHorarioOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TimePickerFragment dialogFragment = new TimePickerFragment(v);
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

                Switch switchAlarme = (Switch) findViewById(R.id.switch_acionarAlarme);
                if (switchAlarme.isChecked()){
                    configuraAlarme();
                }

            }
        }
    };

    private void configuraAlarme() {
        TextClock textClock = (TextClock) findViewById(R.id.textClock);
        Intent i = new Intent(this, ReceptorAlarme.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        AlarmManager gerenciador = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar cal = Calendar.getInstance();

        String stringHour = textClock.getText().toString();
        cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(stringHour.substring(0, 2)));
        cal.set(Calendar.MINUTE, Integer.valueOf(stringHour.substring(3)));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);


        if (cal.getTimeInMillis() < System.currentTimeMillis()) {
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }
        gerenciador.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pi);

        Log.i("Alarme!", "Alarme setado " + cal.getTime().toString());
    }
}
