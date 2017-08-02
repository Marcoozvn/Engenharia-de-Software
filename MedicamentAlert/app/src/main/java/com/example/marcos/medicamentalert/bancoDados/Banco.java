package com.example.marcos.medicamentalert.bancoDados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import models.Medicamento;

/**
 * Created by Marcos on 27/07/2017.
 */

public class Banco extends SQLiteOpenHelper{
    private static final int VERSAO_BANCO = 1;
    private static final String BD_MEDICAMENTOS = "bancodeDados1";
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
                COLUNA_STATUS + " TEXT, " + COLUNA_CODIGO + " INTEGER," +
                " FOREIGN KEY(" + COLUNA_CODIGO + ") REFERENCES tabela_medicamentos(" + COLUNA_CODIGO +"))";
        //Criar aqui tabela de consultas
        db.execSQL(QUERY_COLUNA);
        db.execSQL(QUERY_COLUNA2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void salvarMedicamento(Medicamento medicamento){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, medicamento.getNome());
        values.put(COLUNA_DOSAGEM, medicamento.getDosagem());
        values.put(COLUNA_METRICADOSAGEM, medicamento.getMetricaDosagem());
        values.put(COLUNA_CODIGO, medicamento.hashCode());
        long insert1 = db.insert(TABELA_MEDICAMENTOS, null, values);
        ContentValues values2 = new ContentValues();
        for (String key: medicamento.getAlarmes().keySet()){
            values2.put(COLUNA_HORARIO, key);
            values2.put(COLUNA_STATUS, medicamento.getAlarmes().get(key));
            values2.put(COLUNA_CODIGO, medicamento.hashCode());
            long insert2 = db.insert(TABELA_HORARIOS, null, values2);
        }
        db.close();
    }

    public void atualizaMedicamento(Medicamento medicamento){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, medicamento.getNome());
        values.put(COLUNA_DOSAGEM, medicamento.getDosagem());
        values.put(COLUNA_METRICADOSAGEM, medicamento.getMetricaDosagem());
        int update = db.update(TABELA_MEDICAMENTOS, values, COLUNA_CODIGO + "= ?", new String[]{String.valueOf(medicamento.getCodigo())});
        int delete =  db.delete(TABELA_HORARIOS, COLUNA_CODIGO + "= ?", new String[]{String.valueOf(medicamento.getCodigo())});

        ContentValues values2 = new ContentValues();
        for (String key: medicamento.getAlarmes().keySet()){
            values2.put(COLUNA_HORARIO, key);
            values2.put(COLUNA_STATUS, medicamento.getAlarmes().get(key));
            values2.put(COLUNA_CODIGO, medicamento.getCodigo());
            long insert = db.insert(TABELA_HORARIOS, null, values2);
        }
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
            Cursor cursor1 = this.getWritableDatabase().rawQuery("SELECT horario, status FROM tabela_horarios WHERE " +
                    "tabela_horarios._id = " + String.valueOf(id), null);
            Map<String, Boolean> alarmes = new HashMap<>();
            while (cursor1.moveToNext()){
                String horario = cursor1.getString(cursor1.getColumnIndex(COLUNA_HORARIO));
                String status = cursor1.getString(cursor1.getColumnIndex(COLUNA_STATUS));
                alarmes.put(horario, Boolean.valueOf(status));
            }
            medicamento.setAlarmes(alarmes);
            medicamentos.add(medicamento);
        }
        cursor.close();
        return medicamentos;
    }

    public List<Medicamento> getMedicamentosDoDia(){
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

    public List<Medicamento> getMedicamentosDaSemana(){
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
        db.delete(TABELA_HORARIOS, COLUNA_CODIGO + "= ?", new String[]{String.valueOf(medicamento.getCodigo())});
        db.delete(TABELA_MEDICAMENTOS, COLUNA_CODIGO + " = ?", new String[]{String.valueOf(medicamento.getCodigo())});

        db.close();
    }

    public void deleteTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.print(db.getPath());
        db.delete(TABELA_MEDICAMENTOS, null, null);
        db.close();
    }
}
