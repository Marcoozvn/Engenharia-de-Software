package com.example.marcos.medicamentalert;

/**
 * Created by Lucas Wilker on 08/08/2017.
 */

import com.example.marcos.medicamentalert.activities.CadastroMedicamentosActivity;
import com.example.marcos.medicamentalert.activities.ConsultaActivity;
import com.example.marcos.medicamentalert.bancoDados.Banco;
import com.example.marcos.medicamentalert.models.Consulta;
import com.example.marcos.medicamentalert.models.Medicamento;
import com.example.marcos.medicamentalert.activities.listaMedicamentosActivity;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.example.marcos.medicamentalert.activities.listaMedicamentosActivity.bd;
import static org.junit.Assert.*;


public class ConsultasUnit {

    private List<Consulta> listaConsulta = new ArrayList<Consulta>();
    private List<Medicamento> listaMedicamento = new ArrayList<Medicamento>();

    @Test
    public void criaConsultas() throws Exception {
        Consulta consulta1 = new Consulta("Oftamologista", "Seg, 07 AGO 2017");
        Consulta consulta2 = new Consulta("Dentista", "Ter, 08 AGO 2017");
        Consulta consulta3 = new Consulta("Cardiologista", "Qua, 09 AGO 2017");
        Consulta consulta4 = new Consulta("Sociopata", "Qui, 10 AGO 2017");
        Consulta consulta5 = new Consulta("Psicologo", "Sex, 11 AGO 2017");

        listaConsulta.add(consulta1);
        listaConsulta.add(consulta2);
        listaConsulta.add(consulta3);
        listaConsulta.add(consulta4);
        listaConsulta.add(consulta5);
    }

    @Test
    public void criaMedicamento(){
        Medicamento m1 = new Medicamento("Dorflex", 4, "5");
        Medicamento m2 = new Medicamento("Anador", 3, "7");
        Medicamento m3 = new Medicamento("Decongex", 2, "2");
        Medicamento m4 = new Medicamento("Diclofenaco", 3, "4");
        Medicamento m5 = new Medicamento("Eno", 8, "87");

        listaMedicamento.add(m1);
        listaMedicamento.add(m2);
        listaMedicamento.add(m3);
        listaMedicamento.add(m4);
        listaMedicamento.add(m5);
    }


    @Test
    public void verificaMedicamentoSalvo(){

        criaMedicamento();

        assertEquals(listaMedicamento.get(0).getNome(), "Dorflex");
        assert(listaMedicamento.get(0).getDosagem() == 4);
        assertEquals(listaMedicamento.get(0).getMetricaDosagem(), "5");

        assertEquals(listaMedicamento.get(1).getNome(), "Anador");
        assert(listaMedicamento.get(1).getDosagem() == 3);
        assertEquals(listaMedicamento.get(1).getMetricaDosagem(), "7");

        assertEquals(listaMedicamento.get(2).getNome(), "Decongex");
        assert(listaMedicamento.get(2).getDosagem() == 2);
        assertEquals(listaMedicamento.get(2).getMetricaDosagem(), "2");

    }

    @Test
    public void verificaConsultaSalva() throws Exception {

        criaConsultas();

        assertEquals(listaConsulta.get(0).getTipoConsulta(), "Oftamologista");
        assertEquals(listaConsulta.get(0).getHorarioConsulta(), "Seg, 07 AGO 2017");

        assertEquals(listaConsulta.get(1).getTipoConsulta(), "Dentista");
        assertEquals(listaConsulta.get(1).getHorarioConsulta(), "Ter, 08 AGO 2017");

        assertEquals(listaConsulta.get(2).getTipoConsulta(), "Cardiologista");
        assertEquals(listaConsulta.get(2).getHorarioConsulta(), "Qua, 09 AGO 2017");

        assertEquals(listaConsulta.get(3).getTipoConsulta(), "Sociopata");
        assertEquals(listaConsulta.get(3).getHorarioConsulta(), "Qui, 10 AGO 2017");
    }

}
