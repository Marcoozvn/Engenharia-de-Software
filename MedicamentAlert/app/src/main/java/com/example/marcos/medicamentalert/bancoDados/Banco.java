package com.example.marcos.medicamentalert.bancoDados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.marcos.medicamentalert.Medicamento;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcos on 27/07/2017.
 */

public class Banco extends SQLiteOpenHelper{
    private static final int VERSAO_BANCO = 1;
    private static final String BD_MEDICAMENTOS = "bancoDeDados2";
    private static final String TABELA_MEDICAMENTOS = "tabela_medicamentos";
    private static final String TABELA_HORARIOS = "tabela_horarios";
    private static final String COLUNA_CODIGO = "_id";
    private static final String COLUNA_NOME = "nome";
    private static final String COLUNA_DOSAGEM = "dosagem";
    private static final String COLUNA_METRICADOSAGEM = "metricaDosagem";
    private static final String COLUNA_HORARIO = "horario";
    private static final String COLUNA_STATUS = "status";

    public Banco(Context context) {
        super(context, BD_MEDICAMENTOS, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String QUERY_COLUNA = "CREATE TABLE " + TABELA_MEDICAMENTOS + "( " + COLUNA_CODIGO + " INTEGER PRIMARY KEY, " +
                COLUNA_NOME + " TEXT, " + COLUNA_DOSAGEM + " FLOAT, " + COLUNA_METRICADOSAGEM + " TEXT)";
        String QUERY_COLUNA2 = "CREATE TABLE " + TABELA_HORARIOS + "( " + COLUNA_HORARIO + " TEXT, " +
                COLUNA_STATUS + " BOOLEAN, " + COLUNA_CODIGO + " INTEGER," +
                " FOREIGN KEY(" + COLUNA_CODIGO + ") REFERENCES tabela_medicamentos(" + COLUNA_CODIGO +"))";
        //Criar aqui tabela de consultas
        db.execSQL(QUERY_COLUNA);
        db.execSQL(QUERY_COLUNA2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addMedicamento(Medicamento medicamento){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_CODIGO, medicamento.getCodigo());
        values.put(COLUNA_NOME, medicamento.getNome());
        values.put(COLUNA_DOSAGEM, medicamento.getDosagem());
        values.put(COLUNA_METRICADOSAGEM, medicamento.getMetricaDosagem());

        ContentValues values2 = new ContentValues();
        for (String key: medicamento.getAlarmes().keySet()){
            values2.put(COLUNA_HORARIO, key);
            values2.put(COLUNA_STATUS, medicamento.getAlarmes().get(key));
        }

        db.insert(TABELA_MEDICAMENTOS, null, values);
        db.insert(TABELA_HORARIOS, null, values2);
        db.close();
    }

    public List<Medicamento> getMedicamentosNoBanco(){
        List<Medicamento> medicamentos = new ArrayList<>();
        Cursor cursor = this.getWritableDatabase().rawQuery("SELECT * FROM " + TABELA_MEDICAMENTOS, null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(COLUNA_CODIGO));
            String nome = cursor.getString(cursor.getColumnIndex(COLUNA_NOME));
            float dosagem = cursor.getFloat(cursor.getColumnIndex(COLUNA_DOSAGEM));
            String metricaDosagem = cursor.getString(cursor.getColumnIndex(COLUNA_METRICADOSAGEM));
            Medicamento medicamento = new Medicamento(nome, dosagem, metricaDosagem);
            medicamento.setCodigo(id);
            medicamentos.add(medicamento);
        }
        cursor.close();
        return medicamentos;
    }

    public void removeMedicamento(Medicamento medicamento){
        SQLiteDatabase db = this.getWritableDatabase();
        int codigo = medicamento.getCodigo();
        db.delete(TABELA_MEDICAMENTOS, COLUNA_CODIGO + " = ?", new String[]{String.valueOf(medicamento.getCodigo())});
        /*
        Cursor cursor = this.getWritableDatabase().rawQuery("SELECT * FROM " + TABELA_HORARIOS +
                " ON " + COLUNA_CODIGO + "=" + codigo, null);
        while (cursor.moveToNext()){
            db.delete(TABELA_HORARIOS, COLUNA_CODIGO + " = ?", new String[]{String.valueOf(codigo)});
        }
        */
        db.close();
    }

    public void deleteTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.print(db.getPath());
        db.delete(TABELA_MEDICAMENTOS, null, null);
        db.close();
    }
}
