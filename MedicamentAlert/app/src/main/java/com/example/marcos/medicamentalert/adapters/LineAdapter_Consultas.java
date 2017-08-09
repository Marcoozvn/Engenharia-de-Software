package com.example.marcos.medicamentalert.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.marcos.medicamentalert.activities.listaMedicamentosActivity;
import com.example.marcos.medicamentalert.fragments.DialogText;
import com.example.marcos.medicamentalert.R;

import java.util.List;

import com.example.marcos.medicamentalert.models.Consulta;
import com.example.marcos.medicamentalert.models.Medicamento;

public class LineAdapter_Consultas extends RecyclerView.Adapter<LineHolder_Consultas> {

    private  List<Consulta> mConsulta;
    private Context context;
    public LineAdapter_Consultas(List<Consulta> consulta) {
        this.mConsulta = consulta;
    }

    @Override
    public LineHolder_Consultas onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new LineHolder_Consultas(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_line_view, parent, false));
    }

    @Override
    public void onBindViewHolder(LineHolder_Consultas holder, final int position) {
        holder.tipo_consulta.setText(String.format(mConsulta.get(position).getTipoConsulta()));
        holder.data_consulta.setText(mConsulta.get(position).getHorarioConsulta());
        holder.icone_excluir.setOnClickListener(v -> removerItem(position));

    }

    public void guardaConsulta(Consulta consulta){
        if (consulta.getCodigo() != 0){
            for (int i = 0; i < mConsulta.size(); i++) {
                if (mConsulta.get(i).getCodigo() == consulta.getCodigo()){
                    mConsulta.remove(mConsulta.get(i));
                    mConsulta.add(consulta);
                    notifyItemChanged(i);
                }
            }
        } else {
            consulta.setCodigo(consulta.hashCode());
            mConsulta.add(consulta);
            notifyItemInserted(getItemCount());
        }
    }


    // Método responsável por inserir um novo usuário na lista e notificar que há novos itens.
    private void insertItem(Consulta consulta) {
        mConsulta.add(consulta);
        notifyItemInserted(getItemCount());
    }

    private void removerItem(int position) {
        Consulta consulta = mConsulta.get(position);
        listaMedicamentosActivity.bd.removeConsulta(consulta);
        mConsulta.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mConsulta.size());
    }

    @Override
    public int getItemCount() {
        return mConsulta != null ? mConsulta.size() : 0;
    }

}