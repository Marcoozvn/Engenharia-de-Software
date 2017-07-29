package com.example.marcos.medicamentalert;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Marcos on 28/07/2017.
 */
//Adapter para a listView
public class ListCursorAdapter extends CursorAdapter {

    public ListCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.itens_lista, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvNome = (TextView) view.findViewById(R.id.tvNome);
        TextView tvDosagem = (TextView) view.findViewById(R.id.tvDosagem);
        // Extract properties from cursor
        String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
        int dosagem = cursor.getInt(cursor.getColumnIndexOrThrow("dosagem"));
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));

        SQLiteDatabase db = MainActivity.bd.getReadableDatabase();

        Cursor cursor1;
        String aux = "";
        /*
        do {
            cursor1 = db.rawQuery("SELECT tabela_medicamentos._id, tabela_horarios._id, tabela_horarios.horario" +
                    " FROM tabela_medicamentos, tabela_horarios ON tabela_medicamentos._id = tabela_horarios._id", null);
            int i = cursor1.getColumnIndexOrThrow("horario");
            if (i >= 0){
                aux += cursor1.getString(i) + " - ";
                cursor1.moveToNext();
            }

        } while (!cursor1.isLast());
        */
        // Populate fields with extracted properties
        tvNome.setText(nome);
        tvDosagem.setText(aux);
    }
}
