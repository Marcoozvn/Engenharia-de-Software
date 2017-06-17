package com.example.marcos.medicamentalert;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Medicamento> medicamentos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinnerFrequencia = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterSpinnerFrequencia = ArrayAdapter.createFromResource(this,
                R.array.frequencia_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterSpinnerFrequencia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerFrequencia.setAdapter(adapterSpinnerFrequencia);

        Spinner spinnerDosagem = (Spinner) findViewById(R.id.spinnerDosagem);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterSpinnerDosagem = ArrayAdapter.createFromResource(this,
                R.array.dosagem_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterSpinnerDosagem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerDosagem.setAdapter(adapterSpinnerDosagem);

        Button salvar = (Button) findViewById(R.id.salvar);
        salvar.setOnClickListener(salvarOnClickListener);

        TextClock textClock = (TextClock) findViewById(R.id.textClock);
        textClock.setOnClickListener(dialogFragmentOnClickListener);
    }

    private View.OnClickListener dialogFragmentOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            CustomDialogFragment dialogFragment = new CustomDialogFragment();
            dialogFragment.show(fragmentTransaction, "");
        }
    };

    private View.OnClickListener salvarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText viewNomeMedicamento = (EditText) findViewById(R.id.form_nomeMedicamento);
            EditText viewNomeMedico = (EditText) findViewById(R.id.form_nomeMedico);
            EditText viewDosagem = (EditText) findViewById(R.id.form_quantidadeDosagem);
            Spinner spinnerDosagem = (Spinner) findViewById(R.id.spinnerDosagem);

            String nomeMedicamento = viewNomeMedicamento.getText().toString();
            String nomeMedico = viewNomeMedico.getText().toString();
            String dosagem = viewDosagem.getText().toString();
            String dosagemMetrica = spinnerDosagem.getSelectedItem().toString();

            Medicamento medicamento = new Medicamento(nomeMedicamento, nomeMedico, Integer.valueOf(dosagem), dosagemMetrica);
            medicamentos.add(medicamento);
        }
    };

}
