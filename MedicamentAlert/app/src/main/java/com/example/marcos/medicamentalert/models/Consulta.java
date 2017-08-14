package com.example.marcos.medicamentalert.models;

import java.io.Serializable;

/**
 * Created by Lucas Wilker on 28/07/2017.
 */

public class Consulta implements Serializable{

    private int codigo;
    private String tipoConsulta;
    private String horarioConsulta;
    private String localizacao;
    private String email;
    private String telefone;


    public Consulta(String tipoConsulta, String horarioConsulta, String localizacao, String email, String telefone){

        this.tipoConsulta       = tipoConsulta;
        this.horarioConsulta    = horarioConsulta;
        this.localizacao        = localizacao;
        this.email              = email;
        this.telefone           = telefone;

    }

    public Consulta(String tipoConsulta, String horarioConsulta){
        this.tipoConsulta       = tipoConsulta;
        this.horarioConsulta    = horarioConsulta;
    }

    public int getCodigo(){return codigo;}

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public String getHorarioConsulta() {
        return horarioConsulta;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }


    public void setCodigo(int codigo){
        this.codigo = codigo;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public void setHorarioConsulta(String horarioConsulta) {
        this.horarioConsulta = horarioConsulta;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return tipoConsulta;
    }
}
