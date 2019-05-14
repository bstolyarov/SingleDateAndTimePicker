package com.github.florent37.sample.singledateandtimepicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SingleDatePickerMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_date_picker_activity_main);

        final ArrayList<Long> dates = new ArrayList<>();
        dates.add(1557486900000L); //10 мая 2019 г. 11:15:00
        dates.add(1557557100000L); //11 мая 2019 г. 6:45:00
        dates.add(1557558000000L); //11 мая 2019 г. 7:00:00
        dates.add(1557558900000L); //11 мая 2019 г. 7:15:00
        dates.add(1557559800000L); //11 мая 2019 г. 7:30:00
        dates.add(1557560700000L); //11 мая 2019 г. 7:45:00

        final SingleDateAndTimePicker singleDateAndTimePicker = findViewById(R.id.single_day_picker);
        singleDateAndTimePicker.setMaxDate(null);
        singleDateAndTimePicker.setMinDate(null);
        singleDateAndTimePicker.setAvailableDates(dates);
//        singleDateAndTimePicker.setDefaultDateFromAvailable(new Date(dates.get(0)));
        singleDateAndTimePicker.setCyclic(false);
        singleDateAndTimePicker.addOnDateChangedListener(new SingleDateAndTimePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(String displayed, Date date) {
                Log.d("MainActivity", "displayed: " + displayed);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE d MMM HH mm", Locale.US);
                Log.d("MainActivity", "simpleDateFormat: " + simpleDateFormat.format(singleDateAndTimePicker.getDate()));
//                display(displayed);
//                singleDateAndTimePicker.setVisibility(View.GONE);
            }
        });

        findViewById(R.id.toggleEnabled).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE d MMM HH mm", Locale.US);
                Log.d("MainActivity", "simpleDateFormat: " + simpleDateFormat.format(singleDateAndTimePicker.getDate()));
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(dates.get(2));
                singleDateAndTimePicker.selectDateFromAvailableDates(calendar);
//                singleDateAndTimePicker.setVisibility(View.VISIBLE);
            }
        });
    }

}
