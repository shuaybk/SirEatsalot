package com.shoobyman.sireatsalot.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

public class CalendarDialog extends DialogFragment
                implements DatePickerDialog.OnDateSetListener {

    private Date initDate = new Date();
    private DateChangeListener dateChangeListener;

    public interface DateChangeListener {
        public void onDateChanged(Date date);
    }

    public CalendarDialog() {
        super();
    }

    public CalendarDialog(Date initDate, DateChangeListener dateChangeListener) {
        super();
        this.initDate = initDate;
        this.dateChangeListener = dateChangeListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        c.setTime(initDate);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        dateChangeListener.onDateChanged(c.getTime());
    }
}
