package com.example.marcos.medicamentalert.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marcos on 17/06/2017.
 */

public class Medicamento {
    private int codigo;
    private String nome;
    //private String medicoResponsavel;
    private float dosagem;
    private String metricaDosagem;
    private Map<String, Boolean> alarmes;

    public Medicamento(String nome, float dosagem, String metricaDosagem){
        this.nome = nome;
        //this.medicoResponsavel = medicoResponsavel;
        this.dosagem = dosagem;
        this.alarmes = new HashMap<>();
        this.metricaDosagem = metricaDosagem;
    }

    public String getNome(){ return nome;}

    //public String getMedicoResponsavel(){ return medicoResponsavel;}

    public float getDosagem(){return dosagem;}

    public String getMetricaDosagem(){ return metricaDosagem;}

    public int getCodigo(){return codigo;}

    public void setCodigo(int codigo){
        this.codigo = codigo;
    }

    public void setAlarmes(Map<String, Boolean> alarmes){
        this.alarmes = alarmes;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setDosagem(float dosagem){
        this.dosagem = dosagem;
    }

    public void setMetricaDosagem(String metricaDosagem){
        this.metricaDosagem = metricaDosagem;
    }

    public void tomouMedicamento(String horario){
        alarmes.replace(horario, false, true);
    }

    public Map<String, Boolean> getAlarmes(){
        return  this.alarmes;
    }
}
