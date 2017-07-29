package Activities;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.marcos.medicamentalert.R;

import java.util.ArrayList;
import java.util.List;

import models.Consulta;

public class ConsultaActivity extends AppCompatActivity {

    private List<Consulta> consultas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
    }

    public void salvarConsulta() {

        //pegando os campos
        EditText editText_tipo = (EditText) findViewById(R.id.et_tipo_consulta);
        Button editText_horario = (Button) findViewById(R.id.et_horario);
        EditText editText_localizacao = (EditText) findViewById(R.id.et_localizacao);
        EditText editText_email = (EditText) findViewById(R.id.et_email);
        EditText editText_telefone = (EditText) findViewById(R.id.et_telefone);

        //atribuindo as strings

        if (editText_tipo.getText().toString().isEmpty()){
            exibirMensagem("Digite o tipo da Consulta");
        }

        else if (editText_horario.getText().toString().isEmpty()){
            exibirMensagem("Por favor, digite o horário da consulta");
        }

        else{
            String tipo_consulta        = editText_tipo.getText().toString();
            String horario_consulta     = editText_horario.getText().toString();

            String localizacao_consulta = null;
            String email_consulta = null;
            String telefone_consulta = null;


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
            consultas.add(nova_consulta);
            //voltar pra intent anterior
            //salva no bd
        }

    }

    private void exibirMensagem(String mensagem){
        Toast t = Toast.makeText(this, mensagem, Toast.LENGTH_SHORT);
        t.show();
    }

}
