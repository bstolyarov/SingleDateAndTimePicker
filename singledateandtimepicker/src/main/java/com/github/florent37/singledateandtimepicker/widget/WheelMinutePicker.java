package com.github.florent37.singledateandtimepicker.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

import com.github.florent37.singledateandtimepicker.DateHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.github.florent37.singledateandtimepicker.widget.SingleDateAndTimeConstants.MAX_MINUTES;
import static com.github.florent37.singledateandtimepicker.widget.SingleDateAndTimeConstants.MIN_MINUTES;
import static com.github.florent37.singledateandtimepicker.widget.SingleDateAndTimeConstants.STEP_MINUTES_DEFAULT;

public class WheelMinutePicker extends WheelPicker<String> {

    private int stepMinutes;

    private OnMinuteChangedListener onMinuteChangedListener;
    private OnFinishedLoopListener onFinishedLoopListener;

    public WheelMinutePicker(Context context) {
        super(context);
    }

    public WheelMinutePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        stepMinutes = STEP_MINUTES_DEFAULT;
    }

    @Override
    protected String initDefault() {
        return getFormattedValue(Calendar.getInstance().get(Calendar.MINUTE));
    }

    @Override
    protected List<String> generateAdapterValues() {
        final List<String> minutes = new ArrayList<>();
        for (int min = MIN_MINUTES; min <= MAX_MINUTES; min += stepMinutes) {
            minutes.add(getFormattedValue(min));
        }
        return minutes;
    }

    @Override
    protected List<String> generateAvailableAdapterValues() {
        List<String> minutes;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(WheelDayPicker.DAY_AND_HOUR_FORMAT_PATTERN, getCurrentLocale());
        String formattedDayAndHour;
        String formattedMinute;
        Date tempDate;
        for (Long date : availableDates) {
            tempDate = new Date(date);
            formattedDayAndHour = simpleDateFormat.format(tempDate);
            formattedMinute = getFormattedValue(tempDate);
            if (sortedAvailableDates.containsKey(formattedDayAndHour)) {
                minutes = sortedAvailableDates.get(formattedDayAndHour);
                if (minutes != null && Collections.frequency(minutes, formattedMinute) == 0) {
                    minutes.add(formattedMinute);
                    sortedAvailableDates.put(formattedDayAndHour, minutes);
                }
            } else {
                minutes = new ArrayList<>();
                minutes.add(formattedMinute);
                sortedAvailableDates.put(formattedDayAndHour, minutes);
            }
        }
        return getAvailableDates(null);
    }

    public List<String> getAvailableDates(String day) {
        if (day == null && !availableDates.isEmpty()) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(WheelDayPicker.DAY_AND_HOUR_FORMAT_PATTERN, getCurrentLocale());
            day = simpleDateFormat.format(new Date(availableDates.get(0)));
        }
        return sortedAvailableDates.get(day);
    }


    private int findIndexOfMinute(int minute) {
        final int itemCount = adapter.getItemCount();
        for (int i = 0; i < itemCount; ++i) {
            final String object = adapter.getItemText(i);
            final Integer value = Integer.valueOf(object);

            if (minute == value) {
                return i;
            }

            if (minute < value) {
                return i - 1;
            }
        }
        return itemCount - 1;

    }

    @Override
    public int findIndexOfDate(@NonNull Date date) {
        return findIndexOfMinute(DateHelper.getMinuteOf(date));
    }

    protected String getFormattedValue(Object value) {
        Object valueItem = value;
        if (value instanceof Date) {
            final Calendar instance = Calendar.getInstance();
            instance.setTime((Date) value);
            valueItem = instance.get(Calendar.MINUTE);
        }
        return String.format(getCurrentLocale(), FORMAT, valueItem);
    }

    public void setStepMinutes(int stepMinutes) {
        if (stepMinutes < 60 && stepMinutes > 0) {
            this.stepMinutes = stepMinutes;
            updateAdapter();
        }
    }

    private int convertItemToMinute(Object item) {
        return Integer.valueOf(String.valueOf(item));
    }

    public int getCurrentMinute() {
        return convertItemToMinute(adapter.getItem(getCurrentItemPosition()));
    }

    public WheelMinutePicker setOnMinuteChangedListener(OnMinuteChangedListener onMinuteChangedListener) {
        this.onMinuteChangedListener = onMinuteChangedListener;
        return this;
    }

    public WheelMinutePicker setOnFinishedLoopListener(OnFinishedLoopListener onFinishedLoopListener) {
        this.onFinishedLoopListener = onFinishedLoopListener;
        return this;
    }

    @Override
    protected void onItemSelected(int position, String item) {
        super.onItemSelected(position, item);
        if (onMinuteChangedListener != null) {
            onMinuteChangedListener.onMinuteChanged(this, convertItemToMinute(item));
        }
    }

    @Override
    protected void onFinishedLoop() {
        super.onFinishedLoop();
        if (onFinishedLoopListener != null) {
            onFinishedLoopListener.onFinishedLoop(this);
        }
    }

    public interface OnMinuteChangedListener {
        void onMinuteChanged(WheelMinutePicker picker, int minutes);
    }

    public interface OnFinishedLoopListener {
        void onFinishedLoop(WheelMinutePicker picker);
    }
}