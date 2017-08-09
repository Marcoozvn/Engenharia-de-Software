package com.example.marcos.medicamentalert.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.marcos.medicamentalert.R;

/**
 * Created by Marcos on 31/07/2017.
 */

public class RelatorioListHolder extends RecyclerView.ViewHolder {
    public TextView nomeMedicamento;
    public TextView horarioMedicamento;
    public ImageButton deletaMedicamento;

    public RelatorioListHolder(View itemView) {
        super(itemView);
        this.nomeMedicamento = (TextView) itemView.findViewById(R.id.nomeMedicamento2);
        this.horarioMedicamento = (TextView) itemView.findViewById(R.id.horarioMedicamento);
        this.deletaMedicamento = (ImageButton) itemView.findViewById(R.id.statusMedicamento);
    }
}
