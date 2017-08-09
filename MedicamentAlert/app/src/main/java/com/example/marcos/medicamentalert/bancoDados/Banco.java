package com.example.marcos.medicamentalert.bancoDados;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.marcos.medicamentalert.models.Consulta;
import com.example.marcos.medicamentalert.models.Medicamento;

/**
 * Created by Marcos on 27/07/2017.
 */

public class Banco extends SQLiteOpenHelper{

    private static final int VERSAO_BANCO = 1;
    private static final String BD_MEDICAMENTOS = "bancodeDadosAplicacao";

    //TABELA MEDICAMENTO
    private static final String TABELA_MEDICAMENTOS = "tabela_medicamentos";
    private static final String TABELA_HORARIOS = "tabela_horarios";
    private static final String COLUNA_CODIGO = "_id";
    private static final String COLUNA_NOME = "nome";
    private static final String COLUNA_DOSAGEM = "dosagem";
    private static final String COLUNA_METRICADOSAGEM = "metricaDosagem";
    private static final String COLUNA_HORARIO = "horario";
    private static final String COLUNA_STATUS = "status";
    private static final String COLUNA_DATA = "data";
    private static final String COLUNA_SITUACAO = "situacao";

    //TABELA CONSULTA
    private static final String     TABELA_CONSULTA = "tabela_consultas";
    private static final String COLUNA_CODIGO_CONSULTA = "id";
    private static final String COLUNA_TIPO = "tipo";
    private static final String COLUNA_HORARIO_CONSULTA = "coluna_horario_consulta";
    private static final String COLUNA_LOCALIZACAO = "localizacao";
    private static final String COLUNA_EMAIL = "email";
    private static final String COLUNA_TELEFONE = "telefone";


    public Banco(Context context) {
        super(context, BD_MEDICAMENTOS, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String QUERY_COLUNA = criaTabelaMedicamentos();
        String QUERY_COLUNA2 = criaTabelaHorarios();
        String QUERY_CONSULTA = criaTabelaConsulta();

        //Criar aqui tabela de consultas
        db.execSQL(QUERY_COLUNA);
        db.execSQL(QUERY_COLUNA2);
        db.execSQL(QUERY_CONSULTA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private String criaTabelaMedicamentos(){

        String QUERY_COLUNA = "CREATE TABLE " + TABELA_MEDICAMENTOS + "( " +
                COLUNA_CODIGO + " INTEGER PRIMARY KEY, " +
                COLUNA_NOME + " TEXT, " +
                COLUNA_DOSAGEM + " FLOAT, " +
                COLUNA_DATA + " TEXT, " +
                COLUNA_SITUACAO + " TEXT, " +
                COLUNA_METRICADOSAGEM + " TEXT)";
        return QUERY_COLUNA;
    }

    private String criaTabelaHorarios(){
        String QUERY_COLUNA2 = "CREATE TABLE " + TABELA_HORARIOS + "( " +
                COLUNA_HORARIO + " TEXT, " +
                COLUNA_STATUS + " TEXT, " +
                COLUNA_CODIGO + " INTEGER," +
                " FOREIGN KEY(" + COLUNA_CODIGO + ") REFERENCES tabela_medicamentos(" + COLUNA_CODIGO +"))";

        return QUERY_COLUNA2;
    }

    private String criaTabelaConsulta(){

        String query_consulta = "CREATE TABLE " + TABELA_CONSULTA + "( " +
                COLUNA_CODIGO_CONSULTA + " INTEGER PRIMARY KEY, " +
                COLUNA_TIPO + " TEXT, " +
                COLUNA_HORARIO_CONSULTA + " TEXT, " +
                COLUNA_LOCALIZACAO + " TEXT, " +
                COLUNA_EMAIL + " TEXT, " +
                COLUNA_TELEFONE + " TEXT)";
        return query_consulta;
    }

    public void salvarMedicamento(Medicamento medicamento){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, medicamento.getNome());
        values.put(COLUNA_DOSAGEM, medicamento.getDosagem());
        values.put(COLUNA_METRICADOSAGEM, medicamento.getMetricaDosagem());
        values.put(COLUNA_DATA, medicamento.getData());
        values.put(COLUNA_SITUACAO, medicamento.getSituacaoTomado());
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

    public void salvarConsulta(Consulta consulta){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_CODIGO_CONSULTA, consulta.hashCode());
        values.put(COLUNA_TIPO, consulta.getTipoConsulta());
        values.put(COLUNA_HORARIO_CONSULTA, consulta.getHorarioConsulta());
        values.put(COLUNA_LOCALIZACAO, consulta.getLocalizacao());
        values.put(COLUNA_EMAIL, consulta.getEmail());
        values.put(COLUNA_TELEFONE, consulta.getTelefone());
        long insert1 = db.insert(TABELA_CONSULTA, null, values);
        db.close();
    }


    @SuppressLint("LongLogTag")
    public void atualizaMedicamento(Medicamento medicamento){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, medicamento.getNome());
        values.put(COLUNA_DOSAGEM, medicamento.getDosagem());
        values.put(COLUNA_METRICADOSAGEM, medicamento.getMetricaDosagem());
        values.put(COLUNA_DATA, medicamento.getData());

        values.put(COLUNA_SITUACAO, medicamento.getSituacaoTomado());
        Log.d(medicamento.getSituacaoTomado(), String.valueOf(medicamento.getCodigo()));
        int delete =  db.delete(TABELA_HORARIOS, COLUNA_CODIGO + "= ?", new String[]{String.valueOf(medicamento.getCodigo())});
        int update = db.update(TABELA_MEDICAMENTOS, values, COLUNA_CODIGO + "= ?", new String[]{String.valueOf(medicamento.getCodigo())});

        ContentValues values2 = new ContentValues();
        for (String key: medicamento.getAlarmes().keySet()){
            Log.i("Horário " + key + " inserido no id:", String.valueOf(medicamento.getCodigo()));
            values2.put(COLUNA_HORARIO, key);
            values2.put(COLUNA_STATUS, medicamento.getAlarmes().get(key));
            values2.put(COLUNA_CODIGO, medicamento.getCodigo());
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
            String data = cursor.getString(cursor.getColumnIndex(COLUNA_DATA));
            String tomado = cursor.getString(cursor.getColumnIndex(COLUNA_SITUACAO));
            String metricaDosagem = cursor.getString(cursor.getColumnIndex(COLUNA_METRICADOSAGEM));



            Medicamento medicamento = new Medicamento(nome, dosagem, metricaDosagem, data, tomado);
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


    public List<Consulta> getConsultaNoBanco(){
        List<Consulta> consultas = new ArrayList<>();
        Cursor cursor = this.getWritableDatabase().rawQuery("SELECT * FROM " + TABELA_CONSULTA, null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(COLUNA_CODIGO_CONSULTA));
            String nome = cursor.getString(cursor.getColumnIndex(COLUNA_TIPO));
            String horario = cursor.getString(cursor.getColumnIndex(COLUNA_HORARIO_CONSULTA));
            String localizacao = cursor.getString(cursor.getColumnIndex(COLUNA_LOCALIZACAO));
            String email = cursor.getString(cursor.getColumnIndex(COLUNA_EMAIL));
            String telefone = cursor.getString(cursor.getColumnIndex(COLUNA_TELEFONE));
            Consulta consulta = new Consulta(nome, horario, localizacao, email, telefone);
            consulta.setCodigo(id);
//            Cursor cursor1 = this.getWritableDatabase().rawQuery("SELECT horario, status FROM tabela_horarios WHERE " +
//                    "tabela_horarios._id = " + String.valueOf(id), null);
//            Map<String, Boolean> alarmes = new HashMap<>();
//            while (cursor1.moveToNext()){
//                String horario = cursor1.getString(cursor1.getColumnIndex(COLUNA_HORARIO));
//                String status = cursor1.getString(cursor1.getColumnIndex(COLUNA_STATUS));
//                alarmes.put(horario, Boolean.valueOf(status));
//            }
//            medicamento.setAlarmes(alarmes);
            consultas.add(consulta);
        }
        cursor.close();
        return consultas;
    }






    public void removeMedicamento(Medicamento medicamento){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABELA_HORARIOS, COLUNA_CODIGO + "= ?", new String[]{String.valueOf(medicamento.getCodigo())});
        db.delete(TABELA_MEDICAMENTOS, COLUNA_CODIGO + " = ?", new String[]{String.valueOf(medicamento.getCodigo())});

        db.close();
    }

    public void removeConsulta(Consulta consulta){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABELA_CONSULTA, COLUNA_CODIGO_CONSULTA + "= ?", new String[]{String.valueOf(consulta.getCodigo())});
        db.close();
    }


    public void atualizaTabelaHorario(String horario, int id) {
        Log.i("Horário para atualizar:", horario);
        Log.i("No id", String.valueOf(id));
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_STATUS, "true");
        int i = db.update(TABELA_HORARIOS, values, COLUNA_CODIGO + "=" + id + " AND " + COLUNA_HORARIO + "= '" + horario + "'", null);
        Log.i("afetados", String.valueOf(i));
        /*
        db.rawQuery("UPDATE " + TABELA_HORARIOS + " SET " + COLUNA_STATUS + " = true WHERE " + COLUNA_CODIGO +
        " = " + id + " AND " + COLUNA_HORARIO + " = " + horario, null);
        */
    }
}
