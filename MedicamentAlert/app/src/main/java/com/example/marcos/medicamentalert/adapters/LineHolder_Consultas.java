package com.example.marcos.medicamentalert.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.marcos.medicamentalert.R;

/**
 * Created by Lucas Wilker on 31/07/2017.
 */

public class LineHolder_Consultas extends RecyclerView.ViewHolder {

    public TextView tipo_consulta;
    public TextView data_consulta;
    public ImageButton icone_info;
    public ImageButton icone_excluir;

    public LineHolder_Consultas(View itemView) {
        super(itemView);
        tipo_consulta = (TextView) itemView.findViewById(R.id.main_line_title);
        data_consulta = (TextView) itemView.findViewById(R.id.horario_consulta);
        icone_info = (ImageButton) itemView.findViewById(R.id.info_consulta);
        icone_excluir = (ImageButton) itemView.findViewById(R.id.excluirConsulta);
    }
}
