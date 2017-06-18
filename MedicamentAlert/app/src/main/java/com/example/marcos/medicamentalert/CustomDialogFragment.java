package com.example.marcos.medicamentalert;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TimePicker;

/**
 * Created by Marcos on 17/06/2017.
 */
//EXCLUIR DEPOIS, Usei o TimePickerFragment para fazer o que ela faria
    //Talvez precise dela na hora de configurar o alarme. VER ISSO DEPOIS


public class CustomDialogFragment extends DialogFragment {
    Bundle bundle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.bundle = savedInstanceState;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        final View view = inflater.inflate(R.layout.timepicker, container);

        Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePicker timePicker = (TimePicker) view.findViewById(R.id.timePicker);
                TextClock textClock = (TextClock) getActivity().findViewById(R.id.textClock);
                int hora = timePicker.getHour();
                int minuto = timePicker.getMinute();
                textClock.setText(hora + ":" + minuto);
                dismiss();
            }
        });
        return view;
    }
}
