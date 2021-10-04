package com.example.malert.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.fragment.app.DialogFragment;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.malert.helper.ApiCall;
import com.example.malert.adapter.AutoSuggestAdapter;
import com.example.malert.MyApplication;
import com.example.malert.R;
import com.example.malert.helper.Reminder;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddMedicineActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    private AutoSuggestAdapter autoSuggestAdapter;
    private Handler handler;
    private TextView selectedMedicineName;
    EditText dateAndTime;
    EditText description;
    private int mYear, mMonth, mDay, mHour, mMinute;

    private Calendar timeAndDateSelector = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        selectedMedicineName = findViewById(R.id.selectedMedicineNameTV);
        description = findViewById(R.id.descriptionET);
        dateAndTime = findViewById(R.id.dateAndTimeET);

        final AppCompatAutoCompleteTextView autoCompleteTextView = findViewById(R.id.auto_complete_edit_text);
        autoSuggestAdapter = new AutoSuggestAdapter(this, android.R.layout.simple_dropdown_item_1line);


        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if (msg.what == TRIGGER_AUTO_COMPLETE) {
                    if (!TextUtils.isEmpty(autoCompleteTextView.getText())) {
                        makeApiCall(autoCompleteTextView.getText().toString());
                    }
                }
                return false;
            }
        });

        autoCompleteTextView.setThreshold(2);
        autoCompleteTextView.setAdapter(autoSuggestAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedMedicineName.setText(autoSuggestAdapter.getObject(position));
            }
        });

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE, AUTO_COMPLETE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Do nothing
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Do nothing
            }

        });

        //Date and Time function
        Button button = findViewById(R.id.dateAndTimeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new DatePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        Button addData = findViewById(R.id.addReminder);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToDataBase();
            }
        });

        Button cancelData = findViewById(R.id.cancelReminder);
        cancelData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void makeApiCall(String text) {
        ApiCall.make(getApplicationContext(), text, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("datacheck", response);
                //parsing logic, please change it as per your requirement
                List<String> stringList = new ArrayList<>();
                try {
                    Log.i("datacheck2", "true");
                    JSONArray response1 = new JSONArray(response);

                    for (int i = 0; i < response1.length(); i++) {
                        JSONObject row = response1.getJSONObject(i);
                        stringList.add(row.getString("brand_name"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //IMPORTANT: set data here and notify
                autoSuggestAdapter.setData(stringList);
                autoSuggestAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error.printStackTrace();
                //Log.i("API error", error.getMessage());
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        timeAndDateSelector.set(Calendar.HOUR_OF_DAY, hourOfDay);
        timeAndDateSelector.set(Calendar.MINUTE, minute);
        timeAndDateSelector.set(Calendar.SECOND, 0);
        setDate();
    }


    private void setDate() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        timeAndDateSelector.set(Calendar.MONTH, monthOfYear);
                        timeAndDateSelector.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        timeAndDateSelector.set(Calendar.YEAR, year);
                        updateTimeText(timeAndDateSelector);
                        //startAlarm(c);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void updateTimeText(Calendar timeAndDateSelector) {
        String time = DateFormat.getTimeInstance(DateFormat.SHORT).format(timeAndDateSelector.getTime());
        String d = String.valueOf(timeAndDateSelector.get(Calendar.DAY_OF_MONTH));
        String m = String.valueOf(timeAndDateSelector.get(Calendar.MONTH) + 1);
        String y = String.valueOf(timeAndDateSelector.get(Calendar.YEAR));
        String finalDateAndTime = time + " " + d + "/" + m + "/" + y;
        dateAndTime.setText(finalDateAndTime);
    }

    private void saveDataToDataBase(){
        String name = String.valueOf(selectedMedicineName.getText());
        String description = this.description.getText().toString();
        String dateAndTime = this.dateAndTime.getText().toString();

        if (!name.isEmpty() && !description.isEmpty() && !dateAndTime.isEmpty())
        {
            //save data
            MyApplication.myDatabaseAdapter.insertData(name, description, dateAndTime);
            Toast.makeText(getApplicationContext(), "Data saved", Toast.LENGTH_SHORT).show();
            startAlarm(timeAndDateSelector, name);
        } else
        {
            Toast.makeText(getApplicationContext(), "Cannot save data, as some of it is empty", Toast.LENGTH_LONG).show();
        }
    }

    private void startAlarm(Calendar calendar, String name) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, Reminder.class);
        intent.putExtra("name", name);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (calendar.after(Calendar.getInstance())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                finish();
            }
        } else
        {
            Toast.makeText(getApplicationContext(), "Date and time has to be after the current date and time", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}