package com.example.marcos.medicamentalert.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.marcos.medicamentalert.fragments.DialogText;
import com.example.marcos.medicamentalert.R;

import java.util.List;

import com.example.marcos.medicamentalert.models.Consulta;

public class LineAdapter_Consultas extends RecyclerView.Adapter<LineHolder_Consultas> {

    private final List<Consulta> mConsulta;
    private DialogText dialogText = new DialogText();


    public LineAdapter_Consultas(List<Consulta> consulta) {
        mConsulta = consulta;
    }

    @Override
    public LineHolder_Consultas onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LineHolder_Consultas(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_line_view, parent, false));
    }

    @Override
    public void onBindViewHolder(LineHolder_Consultas holder, final int position) {
        holder.tipo_consulta.setText(String.format(mConsulta.get(position).getTipoConsulta()));
        holder.icone_excluir.setOnClickListener(v -> removerItem(position));

    }

    // Método responsável por inserir um novo usuário na lista e notificar que há novos itens.
    private void insertItem(Consulta consulta) {
        mConsulta.add(consulta);
        notifyItemInserted(getItemCount());
    }

    private void removerItem(int position) {
        mConsulta.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mConsulta.size());
    }

    @Override
    public int getItemCount() {
        return mConsulta != null ? mConsulta.size() : 0;
    }

}