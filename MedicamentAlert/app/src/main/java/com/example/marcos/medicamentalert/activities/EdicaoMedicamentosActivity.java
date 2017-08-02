package com.example.marcos.medicamentalert.activities;

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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.marcos.medicamentalert.R;
import com.example.marcos.medicamentalert.fragments.ReceptorAlarme;
import com.example.marcos.medicamentalert.fragments.TimePickerFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.example.marcos.medicamentalert.models.Medicamento;

/**
 * Created by Marcos on 01/08/2017.
 */

public class EdicaoMedicamentosActivity extends AppCompatActivity {
    private int ultimoTextClock = R.id.textView_horarioLembrete_edicao;
    private int numTextClocks = 0;
    private Medicamento medicamento;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edicao_medicamentos);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.mytoolbarEdicao);
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
        medicamento.setAlarmes(populaMapa(intent.getStringArrayListExtra("Alarmes")));

        Spinner spinnerDosagem = (Spinner) findViewById(R.id.spinnerDosagem_edicao);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterSpinnerDosagem = ArrayAdapter.createFromResource(this,
                R.array.dosagem_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterSpinnerDosagem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerDosagem.setAdapter(adapterSpinnerDosagem);

        Button salvar = (Button) findViewById(R.id.salvarEdicao);
        salvar.setOnClickListener(salvarOnClickListener);

        Button adicionaHorario = (Button) findViewById(R.id.botaoAdicionaHorario_edicao);
        adicionaHorario.setOnClickListener(adicionaHorarioOnClickListener);
        preencheDosagemMedicamento();
        preencheHorarioMedicamento();
        preencheNomeMedicamento();
    }

    private Map<String, Boolean> populaMapa(ArrayList<String> alarmes){
        Map<String, Boolean> mapa = new HashMap<>();
        for (String alarme : alarmes) {
            mapa.put(alarme, false);
        }
        return mapa;
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
        EditText nomeMedicamento = (EditText) findViewById(R.id.form_nomeMedicamento_edicao);
        nomeMedicamento.setText(medicamento.getNome());
    }

    private void preencheHorarioMedicamento(){
        for (String horario : medicamento.getAlarmes().keySet()) {
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayoutHorarioLembrete);
            TextView textClockaux = new TextView(this);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.BELOW, ultimoTextClock);
            textClockaux.setLayoutParams(layoutParams);
            switch (numTextClocks){
                case 0:
                    textClockaux.setId(R.id.textClockEdicao1);
                    break;
                case 1:
                    textClockaux.setId(R.id.textClockEdicao2);
                    break;
                case 2:
                    textClockaux.setId(R.id.textClockEdicao3);
                    break;
                case 3:
                    textClockaux.setId(R.id.textClockEdicao4);
                    break;
                case 4:
                    textClockaux.setId(R.id.textClockEdicao5);
                    break;
                case 5:
                    textClockaux.setId(R.id.textClockEdicao6);
                    break;
                case 6:
                    textClockaux.setId(R.id.textClockEdicao7);
                    break;
            }

            textClockaux.setTextAppearance(this, android.R.style.TextAppearance_Large);
            textClockaux.setTextColor(getResources().getColor(R.color.colorPrimary));
            textClockaux.setPadding(0, 0, 0, 8);
            //textClockaux.set
            textClockaux.setText(horario);
            relativeLayout.addView(textClockaux);
            textClockaux.setOnClickListener(escolheHorarioOnClickListener);
            ultimoTextClock = textClockaux.getId();
            numTextClocks++;
        }
    }

    private void preencheDosagemMedicamento(){
        EditText dosagemMedicamento = (EditText) findViewById(R.id.form_quantidadeDosagem_edicao);
        dosagemMedicamento.setText(String.valueOf(medicamento.getDosagem()));
        Spinner metricaDosagem = (Spinner) findViewById(R.id.spinnerDosagem_edicao);
        if (medicamento.getMetricaDosagem().equals("ml")){
            metricaDosagem.setSelection(getResources().getIntArray(R.array.dosagem_array)[0]);
        }
        else{
            metricaDosagem.setSelection(getResources().getIntArray(R.array.dosagem_array)[1]);
        }

    }

    public void editaNomeMedicamento(View view){
        view.setForeground(getDrawable(R.color.transparent));
        EditText editText = (EditText) findViewById(R.id.form_nomeMedicamento_edicao);
        Button button = (Button) findViewById(R.id.adicionarConsulta_edicao);
        button.setEnabled(true);
        editText.setEnabled(true);
    }

    public void editaHorarioMedicamento(View view){
        view.setForeground(getDrawable(R.color.transparent));
        Switch aSwitch = (Switch) findViewById(R.id.switch_acionarAlarme_edicao);
        Button button = (Button) findViewById(R.id.botaoAdicionaHorario_edicao);
        button.setEnabled(true);
        aSwitch.setEnabled(true);

    }

    public void editaDosagemMedicamento(View view){
        view.setForeground(getDrawable(R.color.transparent));
        EditText editText = (EditText) findViewById(R.id.form_quantidadeDosagem_edicao);
        editText.setEnabled(true);
    }
    private void adicionaHorario(){
        if (numTextClocks < 7) {
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayoutHorarioLembrete);
            TextClock textClockaux = new TextClock(this);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.BELOW, ultimoTextClock);
            textClockaux.setLayoutParams(layoutParams);
            switch (numTextClocks) {
                case 0:
                    textClockaux.setId(R.id.textClockEdicao1);
                    break;
                case 1:
                    textClockaux.setId(R.id.textClockEdicao2);
                    break;
                case 2:
                    textClockaux.setId(R.id.textClockEdicao3);
                    break;
                case 3:
                    textClockaux.setId(R.id.textClockEdicao4);
                    break;
                case 4:
                    textClockaux.setId(R.id.textClockEdicao5);
                    break;
                case 5:
                    textClockaux.setId(R.id.textClockEdicao6);
                    break;
                case 6:
                    textClockaux.setId(R.id.textClockEdicao7);
                    break;
            }

            textClockaux.setTextAppearance(this, android.R.style.TextAppearance_Large);
            textClockaux.setTextColor(getResources().getColor(R.color.colorPrimary));
            textClockaux.setPadding(0, 0, 0, 8);
            relativeLayout.addView(textClockaux);
            textClockaux.setOnClickListener(escolheHorarioOnClickListener);
            ultimoTextClock = textClockaux.getId();
            numTextClocks++;
        }
    }
    private View.OnClickListener adicionaHorarioOnClickListener = v -> adicionaHorario();

    private View.OnClickListener escolheHorarioOnClickListener = v -> {
        TimePickerFragment dialogFragment = new TimePickerFragment(v);
        dialogFragment.show(getFragmentManager(), "TimePicker");
    };

    private View.OnClickListener salvarOnClickListener = v -> {
        Boolean camposPreenchidos = true;
        EditText viewNomeMedicamento = (EditText) findViewById(R.id.form_nomeMedicamento_edicao);
        if( viewNomeMedicamento.getText().toString().length() == 0 ) {
            camposPreenchidos = false;
            viewNomeMedicamento.setError("Por favor, digite o nome do medicamento!");
        }
        //EditText viewNomeMedico = (EditText) findViewById(R.id.form_nomeMedico);
        EditText viewDosagem = (EditText) findViewById(R.id.form_quantidadeDosagem_edicao);
        if( viewDosagem.getText().toString().length() == 0 ) {
            camposPreenchidos = false;
            viewDosagem.setError("Por favor, digite a dosagem!");
        }

        if (camposPreenchidos) {
            Spinner spinnerDosagem = (Spinner) findViewById(R.id.spinnerDosagem_edicao);
            String nomeMedicamento = viewNomeMedicamento.getText().toString();
            String dosagem = viewDosagem.getText().toString();
            String dosagemMetrica = spinnerDosagem.getSelectedItem().toString();

            medicamento.setNome(nomeMedicamento);
            medicamento.setDosagem(Float.valueOf(dosagem));
            medicamento.setMetricaDosagem(dosagemMetrica);
            Map<String, Boolean> alarmes = new HashMap<>();
            for (int i = 1; i <= numTextClocks; i++){
                TextView textclock = (TextView) findViewById(retornaId(i));
                alarmes.put(textclock.getText().toString(), false);
            }
            medicamento.setAlarmes(alarmes);

            listaMedicamentosActivity.bd.atualizaMedicamento(medicamento);
            listaMedicamentosActivity.adapter.guardaMedicamento(medicamento);

            Switch switchAlarme = (Switch) findViewById(R.id.switch_acionarAlarme_edicao);
            if (switchAlarme.isChecked()){
                for (int i = 1; i <= numTextClocks; i++){
                    TextView textclock = (TextView) findViewById(retornaId(i));
                    configuraAlarme(textclock);
                }
            }

            Intent intent = new Intent(this, listaMedicamentosActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    };

    private int retornaId(int i){
        switch (i){
            case 1:
                return R.id.textClockEdicao1;
            case 2:
                return R.id.textClockEdicao2;
            case 3:
                return R.id.textClockEdicao3;
            case 4:
                return R.id.textClockEdicao4;
            case 5:
                return R.id.textClockEdicao5;
            case 6:
                return R.id.textClockEdicao6;
            case 7:
                return R.id.textClockEdicao7;
        }
        return 0;
    }

    private void configuraAlarme(TextView textClock) {
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
