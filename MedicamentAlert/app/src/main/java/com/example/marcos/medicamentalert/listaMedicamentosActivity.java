package com.example.marcos.medicamentalert;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.marcos.medicamentalert.bancoDados.Banco;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcos on 22/07/2017.
 */

public class listaMedicamentosActivity extends AppCompatActivity {
    public static Banco bd;
    public static ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicament_list);
        bd = new Banco(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Medicamentos");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_menu_white_24dp));


        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_layour_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        // Está sendo criado com lista vazia, pois será preenchida posteriormente.
        adapter = new ListAdapter(bd.getMedicamentosNoBanco());
        mRecyclerView.setAdapter(adapter);

        // Configurando um dividr entre linhas, para uma melhor visualização.
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(cadastraMedicamentoOnClickListener);
    }

    private View.OnClickListener cadastraMedicamentoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), CadastroMedicamentosActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(intent);
        }
    };

        //SQLiteDatabase db = MainActivity.bd.getWritableDatabase();
        //Cursor cursor = db.rawQuery("SELECT * from tabela_medicamentos", null);
        //ListCursorAdapter adapter = new ListCursorAdapter(this, cursor);
        //listaDeMedicamentos.setAdapter(adapter);



}
