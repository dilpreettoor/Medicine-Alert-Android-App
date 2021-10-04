package com.example.malert.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.malert.MyApplication;
import com.example.malert.R;
import com.example.malert.data.MedicinePOJO;
import com.google.gson.Gson;

public class ViewMedicineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_medicine);

        String medicinePOJOString = getIntent().getStringExtra("medicinePOJO");
        Gson gson = new Gson();
        final MedicinePOJO medicinePOJO = gson.fromJson(medicinePOJOString, MedicinePOJO.class);

        TextView searchNameTVName = findViewById(R.id.searchNameTVName);
        TextView descriptionTV = findViewById(R.id.descriptionTV);
        TextView dateAndTimeTV = findViewById(R.id.dateAndTimeTV);

        searchNameTVName.setText(medicinePOJO.getName());
        descriptionTV.setText(medicinePOJO.getDescription());
        dateAndTimeTV.setText(medicinePOJO.getDateAndTime());

        Button delete = findViewById(R.id.deleteReminder);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = medicinePOJO.getId();
                MyApplication.myDatabaseAdapter.delete(id);
                Toast.makeText(getApplicationContext(), "Reminder deleted", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}