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
    private String data;
    private String situacaoTomado;
    public Medicamento(String nome, float dosagem, String metricaDosagem, String data, String situacaoTomado){
        this.nome = nome;
        //this.medicoResponsavel = medicoResponsavel;
        this.dosagem = dosagem;
        this.alarmes = new HashMap<>();
        this.metricaDosagem = metricaDosagem;
        this.data = data;
        this.situacaoTomado=situacaoTomado;
    }


    public String getNome(){ return nome;}

    //public String getMedicoResponsavel(){ return medicoResponsavel;}

    public float getDosagem(){return dosagem;}

    public String getMetricaDosagem(){ return metricaDosagem;}
    public String getData(){return data;}
    public int getCodigo(){return codigo;}

    public void setCodigo(int codigo){
        this.codigo = codigo;
    }
    public void setData(){this.data = data;}
    public void setAlarmes(Map<String, Boolean> alarmes){
        this.alarmes = alarmes;
    }

    public void setSituacaoTomado(String situacao){this.situacaoTomado = situacao;}

    public String getSituacaoTomado(){return this.situacaoTomado;}
    public void setNome(String nome){
        this.nome = nome;
    }

    public void setDosagem(float dosagem){
        this.dosagem = dosagem;
    }

    public void setMetricaDosagem(String metricaDosagem){
        this.metricaDosagem = metricaDosagem;
    }



    public Map<String, Boolean> getAlarmes(){
        return  this.alarmes;
    }
}
