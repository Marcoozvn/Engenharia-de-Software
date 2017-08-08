package com.example.marcos.medicamentalert.activities;


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
import android.widget.TextView;

import com.example.marcos.medicamentalert.R;
import com.example.marcos.medicamentalert.fragments.ReceptorAlarme;
import com.example.marcos.medicamentalert.fragments.TimePickerFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.example.marcos.medicamentalert.models.Consulta;
import com.example.marcos.medicamentalert.models.Medicamento;

public class CadastroMedicamentosActivity extends AppCompatActivity {
    private Stack<Integer> ultimoTextClock;
    private int numTextClocks = 1;
    private Consulta nova_consulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_medicamentos);
        ultimoTextClock = new Stack<Integer>();
        ultimoTextClock.push(R.id.textClock1);



        Toolbar myToolbar = (Toolbar) findViewById(R.id.mytoolbarCadastro);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Cadastro de Medicamentos");
        myToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_clear_white_24dp));
        myToolbar.setNavigationOnClickListener(listagemOnClickListener);
        /*
        Spinner spinnerFrequencia = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterSpinnerFrequencia = ArrayAdapter.createFromResource(this,
                R.array.frequencia_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterSpinnerFrequencia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerFrequencia.setAdapter(adapterSpinnerFrequencia);
        */
        Spinner spinnerDosagem = (Spinner) findViewById(R.id.spinnerDosagem_cadastro);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterSpinnerDosagem = ArrayAdapter.createFromResource(this,
                R.array.dosagem_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterSpinnerDosagem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerDosagem.setAdapter(adapterSpinnerDosagem);

        Button salvar = (Button) findViewById(R.id.salvarCadastro);
        salvar.setOnClickListener(salvarOnClickListener);

        Button adicionaHorario = (Button) findViewById(R.id.botaoAdicionaHorario_cadastro);
        adicionaHorario.setOnClickListener(adicionaHorarioOnClickListener);
        TextView textClock = (TextView) findViewById(R.id.textClock1);
        textClock.setOnClickListener(escolheHorarioOnClickListener);

//        Button btn_add_consulta = (Button) findViewById(R.id.add_consulta);

    }

    public void intent_consulta(View view){
        Intent intent_consulta = new Intent(getApplicationContext(), ConsultaActivity.class);
//        intent_consulta.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(intent_consulta, 1);
    }

    private View.OnClickListener listagemOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), listaMedicamentosActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(intent);
        }
    };

    public void removeHorario(View v){
        if (numTextClocks > 1){
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
            relativeLayout.removeView(findViewById(ultimoTextClock.pop()));
            numTextClocks--;
        }
    }

    // ARRUMAR ISSO AQUI! (Tamanho, fonte, padding, posição do botão)
    private View.OnClickListener adicionaHorarioOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (numTextClocks < 7){
                RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout3);
                TextView textClockaux = new TextClock(getApplicationContext());
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.BELOW, ultimoTextClock.peek());
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
                ultimoTextClock.push(textClockaux.getId());
                numTextClocks++;
            }
        }
    };

    private View.OnClickListener escolheHorarioOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TimePickerFragment dialogFragment = new TimePickerFragment(v);
            dialogFragment.show(getFragmentManager(), "TimePicker");
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK){
            nova_consulta = (Consulta) data.getSerializableExtra("Nova_consulta");
        }
    }

    private View.OnClickListener salvarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Boolean camposPreenchidos = true;
            EditText viewNomeMedicamento = (EditText) findViewById(R.id.form_nomeMedicamento_cadastro);
            if( viewNomeMedicamento.getText().toString().length() == 0 ) {
                camposPreenchidos = false;
                viewNomeMedicamento.setError("Por favor, digite o nome do medicamento!");
            }
            //EditText viewNomeMedico = (EditText) findViewById(R.id.form_nomeMedico);
            EditText viewDosagem = (EditText) findViewById(R.id.form_quantidadeDosagem_cadastro);
            if( viewDosagem.getText().toString().length() == 0 ) {
                camposPreenchidos = false;
                viewDosagem.setError("Por favor, digite a dosagem!");
            }

            if (camposPreenchidos) {
                Spinner spinnerDosagem = (Spinner) findViewById(R.id.spinnerDosagem_cadastro);
                String nomeMedicamento = viewNomeMedicamento.getText().toString();
                //String nomeMedico = viewNomeMedico.getText().toString();
                String dosagem = viewDosagem.getText().toString();
                String dosagemMetrica = spinnerDosagem.getSelectedItem().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
                String currentDateandTime = sdf.format(new Date()).split("_")[0];

                Medicamento medicamento = new Medicamento(nomeMedicamento, Float.valueOf(dosagem), dosagemMetrica, currentDateandTime, "nao");
                Map<String, Boolean> alarmes = new HashMap<>();
                for (int i = 1; i <= numTextClocks; i++){
                    System.out.print(numTextClocks + " - " + i);
                    TextView textclock = (TextView) findViewById(retornaId(i));
                    alarmes.put(textclock.getText().toString(), false);
                }
                medicamento.setAlarmes(alarmes);

                listaMedicamentosActivity.bd.salvarMedicamento(medicamento);
                listaMedicamentosActivity.adapter.guardaMedicamento(medicamento);
                if (nova_consulta != null){
                    listaMedicamentosActivity.bd.salvarConsulta(nova_consulta);
                }

                Switch switchAlarme = (Switch) findViewById(R.id.switch_acionarAlarme_cadastro);
                if (switchAlarme.isChecked()){
                    for (int i = 1; i <= numTextClocks; i++){
                        TextView textclock = (TextView) findViewById(retornaId(i));
                        configuraAlarme(textclock, medicamento.hashCode(), nomeMedicamento, Float.valueOf(dosagem), dosagemMetrica, i);
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

    private void configuraAlarme(TextView textClock, int id, String nome, float dosagem, String metricaDosagem, int count) {
        Intent i = new Intent(this, ReceptorAlarme.class);
        i.putExtra("horario", textClock.getText().toString());
        i.putExtra("id", id);
        i.putExtra("nomeMedicamento", nome);
        i.putExtra("dosagem", dosagem);
        i.putExtra("metricaDosagem", metricaDosagem);
        i.putExtra("count", count);
        PendingIntent pi = PendingIntent.getBroadcast(this, id + count, i, 0);
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
        Log.i("Request code", String.valueOf(count+id));
    }
}
