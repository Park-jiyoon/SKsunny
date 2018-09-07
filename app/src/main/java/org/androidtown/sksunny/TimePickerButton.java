package org.androidtown.sksunny;

import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;



public class TimePickerButton extends AppCompatButton implements TimePickerDialog.OnTimeSetListener{
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
    private TimePickerDialog mDialog;
    private String mDateFormat;

    public TimePickerButton(Context context) {
        super(context);
        initView();
    }

    public TimePickerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public TimePickerButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        initView();

        mDateFormat = context.obtainStyledAttributes(attrs, R.styleable.TimePickerButton).getString(R.styleable.TimePickerButton_dateFormat);
    }

    private void initView() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });
    }

    public void setTimeSetListener(TimePickerDialog.OnTimeSetListener mTimeSetListener) {
        this.mTimeSetListener = mTimeSetListener;
    }

    private void showTimePickerDialog() {
        releaseTimePickerDialog();

        Calendar now = Calendar.getInstance();
        mDialog = new TimePickerDialog(getContext()
                , this
                , now.get(Calendar.HOUR_OF_DAY)
                , now.get(Calendar.MINUTE)
                , false);
        mDialog.show();
    }

    private void releaseTimePickerDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, i);
        calendar.set(Calendar.MINUTE, i1);

        if (TextUtils.isEmpty(mDateFormat)) {
            DateFormat format = SimpleDateFormat.getTimeInstance();
            setText(format.format(calendar.getTime()));
        } else {
            DateFormat format = new SimpleDateFormat(mDateFormat, Locale.getDefault());
            setText(format.format(calendar.getTime()));
        }

        if (this.mTimeSetListener != null) this.mTimeSetListener.onTimeSet(timePicker, i, i1);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        releaseTimePickerDialog();
    }
}