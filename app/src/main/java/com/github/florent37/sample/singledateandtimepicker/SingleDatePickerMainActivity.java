package com.github.florent37.sample.singledateandtimepicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SingleDatePickerMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_date_picker_activity_main);

        ArrayList<Long> dates = new ArrayList<>();
        dates.add(1557486900000L); //10 мая 2019 г. 11:15:00
        dates.add(1557557100000L); //11 мая 2019 г. 6:45:00
        dates.add(1557558000000L); //11 мая 2019 г. 7:00:00
        dates.add(1557558900000L); //11 мая 2019 г. 7:15:00
        dates.add(1557559800000L); //11 мая 2019 г. 7:30:00
        dates.add(1557560700000L); //11 мая 2019 г. 7:45:00

        final SingleDateAndTimePicker singleDateAndTimePicker = (SingleDateAndTimePicker) findViewById(R.id.single_day_picker);
//        singleDateAndTimePicker.setDisplayHours(true);
//        singleDateAndTimePicker.setDisplayMinutes(true);
//        singleDateAndTimePicker.setDisplayDays(false);
//
//        singleDateAndTimePicker.setDisplayMonths(true);
//        singleDateAndTimePicker.setDisplayDaysOfMonth(true);
//        singleDateAndTimePicker.setDisplayYears(false);

        singleDateAndTimePicker.setAvailableDates(dates);
        singleDateAndTimePicker.setDefaultDateFromAvailable(new Date(dates.get(0)), dates);
//        singleDateAndTimePicker.setCyclic(false);
        singleDateAndTimePicker.addOnDateChangedListener(new SingleDateAndTimePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(String displayed, Date date) {
//                display(displayed);
            }
        });

        findViewById(R.id.toggleEnabled).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Date date = singleDateAndTimePicker.getDate();
                String currentDate = "31-01-1970";
                Calendar c = Calendar.getInstance();
                    String[] splittedString = currentDate.split("-");
                    c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(splittedString[0]));
                    c.set(Calendar.MONTH, Integer.parseInt(splittedString[1]) - 1);
                    c.set(Calendar.YEAR, Integer.parseInt(splittedString[2]));

/*                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(c.getTime());
                singleDateAndTimePicker.selectDate(c);*/

//                singleDateAndTimePicker.setEnabled(!singleDateAndTimePicker.isEnabled());
                singleDateAndTimePicker.setDefaultDate(c.getTime());
            }
        });
    }

    private void display(String toDisplay) {
        Toast.makeText(this, toDisplay, Toast.LENGTH_SHORT).show();
    }
}
