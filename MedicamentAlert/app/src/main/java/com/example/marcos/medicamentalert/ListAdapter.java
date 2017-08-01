package com.example.marcos.medicamentalert;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcos on 31/07/2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListHolder> {
    private List<Medicamento> medicamentoList;

    public ListAdapter(List<Medicamento> medicamentos){
        this.medicamentoList = medicamentos;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itens_lista, parent, false));
    }

    @Override
    public void onBindViewHolder(ListHolder holder, int position) {
        holder.nomeMedicamento.setText(medicamentoList.get(position).getNome());
        holder.deletaMedicamento.setOnClickListener(view -> removerItem(position));

    }

    public void adicionaMedicamento(Medicamento medicamento){
        medicamentoList.add(medicamento);
        notifyItemInserted(getItemCount());
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
