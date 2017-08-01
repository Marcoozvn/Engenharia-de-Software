package com.example.marcos.medicamentalert;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextClock;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marcos on 01/08/2017.
 */

public class EdicaoMedicamentosActivity extends AppCompatActivity {
    private int ultimoTextClock = R.id.textClock1;
    private int numTextClocks = 1;
    private Medicamento medicamento;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_medicamento);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Edição de Medicamentos");
        myToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_clear_white_24dp));
        myToolbar.setNavigationOnClickListener(listagemOnClickListener);

        Intent intent = getIntent();
        String nome = intent.getStringExtra("Nome");
        float dosagem = intent.getFloatExtra("Dosagem", 0);
        String metricaDosagem = intent.getStringExtra("metricaDosagem");
        int codigo = intent.getIntExtra("Codigo", 0);
        medicamento = new Medicamento(nome, dosagem, metricaDosagem);
        medicamento.setCodigo(codigo);
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
        TextClock textClock = (TextClock) findViewById(R.id.textClock1);
        textClock.setOnClickListener(escolheHorarioOnClickListener);
        preencheDosagemMedicamento();
        preencheHorarioMedicamento();
        preencheNomeMedicamento();
    }

    private View.OnClickListener listagemOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), listaMedicamentosActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(intent);
        }
    };

    private void preencheNomeMedicamento(){
        EditText nomeMedicamento = (EditText) findViewById(R.id.form_nomeMedicamento);
        nomeMedicamento.setText(medicamento.getNome());
    }

    private void preencheHorarioMedicamento(){
        for (String horario : medicamento.getAlarmes().keySet()) {
            TextClock textClock = adicionaHorario();
            if (textClock != null){
                textClock.setText(horario);
            }
        }
    }

    private void preencheDosagemMedicamento(){
        EditText dosagemMedicamento = (EditText) findViewById(R.id.form_quantidadeDosagem);
        dosagemMedicamento.setText(String.valueOf(medicamento.getDosagem()));
        Spinner metricaDosagem = (Spinner) findViewById(R.id.spinnerDosagem);
        if (medicamento.getMetricaDosagem().equals("ml")){
            metricaDosagem.setSelection(getResources().getIntArray(R.array.dosagem_array)[0]);
        }
        else{
            metricaDosagem.setSelection(getResources().getIntArray(R.array.dosagem_array)[1]);
        }

    }

    public void editaNomeMedicamento(View view){
        FrameLayout layout = (FrameLayout) view;
        layout.setVisibility(View.GONE);
        EditText editText = (EditText) view.findViewById(R.id.form_nomeMedicamento);
        editText.setEnabled(true);
    }

    public void editaHorarioMedicamento(View view){
        FrameLayout layout = (FrameLayout) view;
        layout.setVisibility(View.GONE);
    }

    public void editaDosagemMedicamento(View view){
        FrameLayout layout = (FrameLayout) view;
        layout.setVisibility(View.GONE);
        EditText editText = (EditText) view.findViewById(R.id.form_quantidadeDosagem);
        editText.setEnabled(true);
    }
    private TextClock adicionaHorario(){
        if (numTextClocks < 7){
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
                case 4:
                    textClockaux.setId(R.id.textClock5);
                    break;
                case 5:
                    textClockaux.setId(R.id.textClock6);
                    break;
                case 6:
                    textClockaux.setId(R.id.textClock7);
                    break;
            }

            textClockaux.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Large);
            textClockaux.setTextColor(getResources().getColor(R.color.colorPrimary));
            textClockaux.setPadding(0, 0, 0, 8);
            relativeLayout.addView(textClockaux);
            textClockaux.setOnClickListener(escolheHorarioOnClickListener);
            ultimoTextClock = textClockaux.getId();
            numTextClocks++;
            return textClockaux;
        }
        return null;
    }
    private View.OnClickListener adicionaHorarioOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            adicionaHorario();
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
                Map<String, Boolean> alarmes = new HashMap<>();
                for (int i = 1; i <= numTextClocks; i++){
                    System.out.print(numTextClocks + " - " + i);
                    TextClock textclock = (TextClock) findViewById(retornaId(i));
                    alarmes.put(textclock.getText().toString(), false);
                }
                medicamento.setAlarmes(alarmes);

                listaMedicamentosActivity.bd.addMedicamento(medicamento);
                listaMedicamentosActivity.adapter.adicionaMedicamento(medicamento);

                Switch switchAlarme = (Switch) findViewById(R.id.switch_acionarAlarme);
                if (switchAlarme.isChecked()){
                    for (int i = 1; i <= numTextClocks; i++){
                        TextClock textclock = (TextClock) findViewById(retornaId(i));
                        configuraAlarme(textclock);
                    }
                }
                finish();

            }
        }
    };

    private int retornaId(int i){
        switch (i){
            case 1:
                return R.id.textClock1;
            case 2:
                return R.id.textClock2;
            case 3:
                return R.id.textClock3;
            case 4:
                return R.id.textClock4;
            case 5:
                return R.id.textClock5;
            case 6:
                return R.id.textClock6;
            case 7:
                return R.id.textClock7;
        }
        return 0;
    }

    private void configuraAlarme(TextClock textClock) {
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
