package com.example.marcos.medicamentalert;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Marcos on 31/07/2017.
 */

public class RelatorioListAdapter extends RecyclerView.Adapter<RelatorioListHolder> {
    private List<Medicamento> medicamentoList;

    public RelatorioListAdapter(List<Medicamento> medicamentos){
        this.medicamentoList = medicamentos;
    }

    @Override
    public RelatorioListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RelatorioListHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itens_relatorio, parent, false));
    }

    @Override
    public void onBindViewHolder(RelatorioListHolder holder, int position) {
        holder.nomeMedicamento.setText(medicamentoList.get(position).getNome());
        //holder.deletaMedicamento.setOnClickListener(view -> removerItem(position));

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
