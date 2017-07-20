package com.example.marcos.medicamentalert;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Manuel on 20/07/2017.
 */

public class ListaMedicamentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicament_list);

        List<Medicamento> medicamentos = null;

        Medicamento med1 = new Medicamento("dorflex1", "antonio1", 1, "qwdfwq");
        Medicamento med2 = new Medicamento("dorflex2", "antonio2", 2, "qwdfwq");
        Medicamento med3 = new Medicamento("dorflex3", "antoni3", 3, "qwdfwq");
        Medicamento med4 = new Medicamento("dorflex4", "antonio", 4, "qwdfwq");
        Medicamento med5 = new Medicamento("dorflex5", "antonio", 5, "qwdfwq");
        Medicamento med6 = new Medicamento("dorflex6", "antonio", 6, "qwdfwq");

        medicamentos.add(0,med1);
        medicamentos.add(1,med2);
        medicamentos.add(2,med3);
        medicamentos.add(3,med4);
        medicamentos.add(4,med5);
        medicamentos.add(5,med6);



        ListView listaDeMedicamentos = (ListView) findViewById(R.id.listview);

        //métodos

        ArrayAdapter<Medicamento> adapter = new ArrayAdapter<Medicamento>(this,
                android.R.layout.simple_list_item_1, medicamentos);

        listaDeMedicamentos.setAdapter(adapter);

    }
}
