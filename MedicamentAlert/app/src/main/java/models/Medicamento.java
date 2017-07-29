package models;

/**
 * Created by Marcos on 17/06/2017.
 */

public class Medicamento {

    private String nome;
    //private String medicoResponsavel;
    private double dosagem;
    private String metricaDosagem;

    public Medicamento(String nome, double dosagem, String metricaDosagem){
        this.nome = nome;
        //this.medicoResponsavel = medicoResponsavel;
        this.dosagem = dosagem;
        this.metricaDosagem = metricaDosagem;
    }

    public String getNome(){ return nome;}

    //public String getMedicoResponsavel(){ return medicoResponsavel;}

    public double getDosagem(){return dosagem;}

    public String getMetricaDosagem(){ return metricaDosagem;}
}
