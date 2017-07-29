package com.example.marcos.medicamentalert;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.marcos.medicamentalert.bancoDados.Banco;

/**
 * Created by Marcos on 18/07/2017.
 */

public class MainActivity extends AppCompatActivity {
    public static Banco bd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bd = new Banco(this);

        Button botaoCadastro = (Button) findViewById(R.id.cadastroMedicamentos);
        botaoCadastro.setOnClickListener(cadastroOnClickListener);

        Button botaoListagem = (Button) findViewById(R.id.listaMedicamentos);
        botaoListagem.setOnClickListener(listagemOnClickListener);
        this.deleteDatabase("/data/user/0/com.example.marcos.medicamentalert/databases/bd_medicamentos.db");
    }

    private View.OnClickListener cadastroOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), CadastroMedicamentosActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(intent);
        }
    };

    private View.OnClickListener listagemOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), listaMedicamentosActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(intent);
        }
    };

}
