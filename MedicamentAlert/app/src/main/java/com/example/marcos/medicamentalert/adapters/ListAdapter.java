package com.example.marcos.medicamentalert.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.marcos.medicamentalert.activities.EdicaoMedicamentosActivity;
import com.example.marcos.medicamentalert.activities.listaMedicamentosActivity;
import com.example.marcos.medicamentalert.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.example.marcos.medicamentalert.models.Medicamento;

/**
 * Created by Marcos on 31/07/2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListHolder>{
    private List<Medicamento> medicamentoList;
    private Context context;
    public ListAdapter(List<Medicamento> medicamentos){
        this.medicamentoList = medicamentos;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ListHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itens_lista, parent, false));
    }

    @Override
    public void onBindViewHolder(ListHolder holder, int position) {

        String aux = "";
        aux += medicamentoList.get(position).getNome() + " ";
        for (String horario: medicamentoList.get(position).getAlarmes().keySet()) {
            aux += horario + "->" + medicamentoList.get(position).getAlarmes().get(horario) + " ";
        }

        holder.nomeMedicamento.setText(medicamentoList.get(position).getNome());
        holder.infoMedicamento.setOnClickListener(view -> editaItem(position));
        holder.deletaMedicamento.setOnClickListener(view -> removerItem(position));
    }

    private void editaItem(int position){
        Intent intent = new Intent(context, EdicaoMedicamentosActivity.class);
        Medicamento medicamento = medicamentoList.get(position);
        intent.putExtra("Nome", medicamento.getNome());
        intent.putExtra("Dosagem", medicamento.getDosagem());
        intent.putExtra("metricaDosagem", medicamento.getMetricaDosagem());
        intent.putExtra("Codigo", medicamento.getCodigo());
        intent.putStringArrayListExtra("Alarmes", populaString(medicamento.getAlarmes().keySet()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private ArrayList<String> populaString(Set<String> keySet ){
        ArrayList<String> arrayList= new ArrayList<>();
        for (String key : keySet) {
            arrayList.add(key);
        }
        return arrayList;
    }

    public void guardaMedicamento(Medicamento medicamento){
        if (medicamento.getCodigo() != 0){
            for (int i = 0; i < medicamentoList.size(); i++) {
                if (medicamentoList.get(i).getCodigo() == medicamento.getCodigo()){
                    medicamentoList.remove(medicamentoList.get(i));
                    medicamentoList.add(medicamento);
                    notifyItemChanged(i);
                }
            }
        } else {
            medicamento.setCodigo(medicamento.hashCode());
            medicamentoList.add(medicamento);
            notifyItemInserted(getItemCount());
        }
    }

    private void removerItem(int position) {
        Medicamento medicamento = medicamentoList.get(position);
        listaMedicamentosActivity.bd.removeMedicamento(medicamento);
        medicamentoList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, medicamentoList.size());
    }

    @Override
    public int getItemCount() {
        return medicamentoList != null ? medicamentoList.size() : 0;
    }
}
