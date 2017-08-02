package com.example.marcos.medicamentalert;

/**
 * Created by Marcos on 17/06/2017.
 */

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.text.format.DateFormat;
import android.widget.TextClock;
import android.app.DialogFragment;
import android.app.Dialog;
import java.util.Calendar;
import android.widget.TimePicker;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
    private Bundle bundle = getArguments();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog tpd = new TimePickerDialog(getActivity(), R.style.DialogTheme
                ,this, hour, minute, DateFormat.is24HourFormat(getActivity()));

        return tpd;
    }


    public void onTimeSet(TimePicker view, int hourOfDay, int minute){

        TextClock textClock = (TextClock) getActivity().findViewById(R.id.textClock);
        textClock.setText(hourOfDay + ":" + minute);

    }
}