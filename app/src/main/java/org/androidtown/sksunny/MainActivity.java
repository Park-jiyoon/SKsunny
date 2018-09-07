package org.androidtown.sksunny;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity implements  TimePickerDialog.OnTimeSetListener{

    public static final int REQUEST_CODE_MENU=101;
    Button btnOnetime;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        TimePickerButton timePickerButton = (TimePickerButton) findViewById(R.id.btn_timepicker);
        timePickerButton.setTimeSetListener((TimePickerDialog.OnTimeSetListener) this);

        btnOnetime = (Button)findViewById(R.id.button_test);

        btnOnetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View view) {
                startAlarm(false);
            }
        });
    } // TEST 버튼을 누르면 Notification이랑 Toast 알람이 제공 -> 후에 DB 생성 후 알람기능으로 쓰일 예정

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

    }


    public void onButton_MenuClicked(View v){
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivityForResult(intent,REQUEST_CODE_MENU);
    } // MENU 버튼을 누르면 MENU 화면 호출

    private void startAlarm(boolean isRepeat) {
        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent myIntent;
        PendingIntent pendingIntent;

        myIntent = new Intent(MainActivity.this,AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);

        if(!isRepeat)
            manager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime()+3000, pendingIntent);
        else
            manager.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime()+3000,3000,pendingIntent);

    }
    // ALARM 울리기
}
