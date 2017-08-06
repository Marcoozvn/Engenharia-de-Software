package com.example.marcos.medicamentalert.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;


/**
 * Created by Lucas Wilker on 01/08/2017.
 */

public class DialogText {

    private boolean resposta = true;

    public boolean alertDialog (Context context){

        AlertDialog.Builder alerta = new AlertDialog.Builder(context);
        alerta.setTitle("Aviso");
        alerta.setMessage("Tem certeza que deseja apagar essa consulta ?");
        alerta.setCancelable(false);
        alerta.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resposta = false;
            }
        })
        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resposta = true;
            }
        });

        AlertDialog alertDialog = alerta.create();
        alertDialog.show();
        return resposta;
    }
}
