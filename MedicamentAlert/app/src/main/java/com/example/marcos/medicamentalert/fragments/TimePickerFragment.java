package com.example.marcos.medicamentalert.fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.text.format.DateFormat;
import android.view.View;
import android.app.DialogFragment;
import android.app.Dialog;
import java.util.Calendar;

import android.widget.TextView;
import android.widget.TimePicker;

import com.example.marcos.medicamentalert.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    private Bundle bundle = getArguments();
    private TextView textClock;

    public TimePickerFragment(){
        super();
    }

    public TimePickerFragment(View textClock){
        super();
        this.textClock = (TextView) textClock;

    }

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
        String hourString;
        String minuteString;
        //TextClock textClock = (TextClock) getActivity().findViewById(R.id.textClock);
        if (hourOfDay < 10){
            hourString = "0" + hourOfDay;
        } else { hourString = "" + hourOfDay;}

        if (minute < 10){
            minuteString = "0" + minute;
        } else { minuteString = "" + minute;}

        textClock.setText(hourString + ":" + minuteString);

    }

}