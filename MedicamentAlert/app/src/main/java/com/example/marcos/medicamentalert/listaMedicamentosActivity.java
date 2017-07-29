package com.example.marcos.medicamentalert;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.List;

/**
 * Created by Marcos on 22/07/2017.
 */

public class listaMedicamentosActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicament_list);

        ListView listaDeMedicamentos = (ListView) findViewById(R.id.listview);
        //MainActivity.bd.deleteTable();
//métodos

        SQLiteDatabase db = MainActivity.bd.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from tabela_medicamentos", null);
        ListCursorAdapter adapter = new ListCursorAdapter(this, cursor);
        listaDeMedicamentos.setAdapter(adapter);

    }


}
