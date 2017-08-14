package com.example.marcos.medicamentalert.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.marcos.medicamentalert.adapters.LineAdapter_Consultas;
import com.example.marcos.medicamentalert.R;

import java.util.List;

import com.example.marcos.medicamentalert.models.Consulta;

public class ListagemConsultas extends AppCompatActivity {

    public static LineAdapter_Consultas adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        getSupportActionBar().setTitle("Consultas");

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_layour_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        // Está sendo criado com lista vazia, pois será preenchida posteriormente.
        adapter = new LineAdapter_Consultas(listaMedicamentosActivity.bd.getConsultaNoBanco());
        mRecyclerView.setAdapter(adapter);

        // Configurando um dividr entre linhas, para uma melhor visualização.
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }
}
