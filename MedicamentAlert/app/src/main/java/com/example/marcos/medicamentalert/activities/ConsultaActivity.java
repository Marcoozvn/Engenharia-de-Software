package com.example.marcos.medicamentalert.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.marcos.medicamentalert.R;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.marcos.medicamentalert.models.Consulta;

public class ConsultaActivity extends AppCompatActivity {


    private static List<Consulta> Consultas;
    private Button btn_horario;
    private Calendar dateTime = Calendar.getInstance();

    public static List<Consulta> getInstance(){
        if (Consultas == null){
            Consultas = new ArrayList<Consulta>();
        }
        return Consultas;
    }
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        getSupportActionBar().setTitle("Cadastro Consulta");
        getInstance();

    }

    public void salvarConsulta(View view) {

        //pegando os campos
        EditText editText_tipo = (EditText) findViewById(R.id.et_tipo_consulta);
        btn_horario = (Button) findViewById(R.id.btn_horario);
        EditText editText_localizacao = (EditText) findViewById(R.id.et_localizacao);
        EditText editText_email = (EditText) findViewById(R.id.et_email);
        EditText editText_telefone = (EditText) findViewById(R.id.et_telefone);

        //atribuindo as strings

        if (editText_tipo.getText().toString().isEmpty()){
            editText_tipo.setError("Por favor, digite o tipo da consulta!");
        }

        else if (btn_horario.getText().toString().equalsIgnoreCase("Adicione o Horário da Consulta")){
            exibirMensagem("Por favor, adicione o horário da consulta");
        }

        else if (editText_localizacao.getText().toString().isEmpty()){
            editText_localizacao.setError("Por favor, adicione a localização");
        }

        else{
            String tipo_consulta            = editText_tipo.getText().toString();
            String horario_consulta         = btn_horario.getText().toString();

            String localizacao_consulta     = editText_localizacao.getText().toString();
            String email_consulta           = null;
            String telefone_consulta        = null;


            if (!editText_localizacao.getText().toString().isEmpty()){
                localizacao_consulta = editText_localizacao.getText().toString();
            }

            if (!editText_email.getText().toString().isEmpty()){
                email_consulta       = editText_email.getText().toString();

            }

            if (!editText_telefone.getText().toString().isEmpty()){
                telefone_consulta    = editText_telefone.getText().toString();
            }

            //criar consulta perfeita
            Consulta nova_consulta = new Consulta(tipo_consulta, horario_consulta, localizacao_consulta, email_consulta, telefone_consulta);
            Consultas.add(nova_consulta);
            //voltar pra intent anterior
            //salva no bd
            //intentMedicamento(view);
            exibirMensagem("Consulta Salva com Sucesso");
            intentListagem(view);

        }

    }

    public void intentListagem(View v){
        Intent intent_listagem = new Intent(this, ListagemConsultas.class);
        startActivity(intent_listagem);
    }

    public void adicionarHorarioConsulta(View v){
        btn_horario = (Button) findViewById(R.id.btn_horario);
        updateDate();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            dateTime.set(Calendar.MONTH, month);
            dateTime.set(Calendar.YEAR, year);
            updateTime();
        }
    };

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateTime.set(Calendar.MINUTE, minute);
            updateButtonHorario();
        }
    };

    private void updateDate(){
        new DatePickerDialog(this, d, dateTime.get(Calendar.YEAR), dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateTime(){
        new TimePickerDialog(this, t, dateTime.get(Calendar.HOUR_OF_DAY), dateTime.get(Calendar.MINUTE), true).show();
    }

    private void updateButtonHorario(){
        Format formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm"); //DIA_SEMANA, DIA_MES MES ANO HORA:MINUTO
        String dataFormatada = formatter.format(dateTime.getTime());
        btn_horario.setText(dataFormatada);
    }

    private void exibirMensagem(String mensagem){
        Toast t = Toast.makeText(this, mensagem, Toast.LENGTH_LONG);
        t.setGravity(0, 15, 50);
        t.show();
    }
}
