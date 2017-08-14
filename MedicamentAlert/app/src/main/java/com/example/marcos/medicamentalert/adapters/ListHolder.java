package com.example.marcos.medicamentalert.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.marcos.medicamentalert.R;

/**
 * Created by Marcos on 31/07/2017.
 */

public class ListHolder extends RecyclerView.ViewHolder {
    public TextView nomeMedicamento;
    public TextView dosagemMedicamento;
    public ImageButton infoMedicamento;
    public ImageButton deletaMedicamento;

    public ListHolder(View itemView) {
        super(itemView);
        this.nomeMedicamento = (TextView) itemView.findViewById(R.id.nomeMedicamento);
        this.dosagemMedicamento = (TextView) itemView.findViewById(R.id.dosagemMedicamento);
        this.infoMedicamento = (ImageButton) itemView.findViewById(R.id.infoMedicamento);
        this.deletaMedicamento = (ImageButton) itemView.findViewById(R.id.deletaMedicamento);
    }
}
