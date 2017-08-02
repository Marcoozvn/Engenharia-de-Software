package com.example.marcos.medicamentalert;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Marcos on 31/07/2017.
 */

public class RelatorioListHolder extends RecyclerView.ViewHolder {
    public TextView nomeMedicamento;

    public ImageButton deletaMedicamento;

    public RelatorioListHolder(View itemView) {
        super(itemView);
        this.nomeMedicamento = (TextView) itemView.findViewById(R.id.nomeMedicamento2);

        this.deletaMedicamento = (ImageButton) itemView.findViewById(R.id.statusMedicamento);
    }
}
