package com.example.malert.ui;

import android.content.Intent;
import android.os.Bundle;
import com.example.malert.MyApplication;
import com.example.malert.R;
import com.example.malert.adapter.ListOfMedicineAdapter;
import com.example.malert.data.MedicinePOJO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView listOfMedicineRV;
    ListOfMedicineAdapter listOfMedicineAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listOfMedicineRV = findViewById(R.id.listOfRemindersRV);
        listOfMedicineRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        listOfMedicineRV.addItemDecoration(
                new DividerItemDecoration(
                        getApplicationContext(),
                        DividerItemDecoration.VERTICAL
                )
        );

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<MedicinePOJO> medicinePOJOList = MyApplication.myDatabaseAdapter.getData();
        listOfMedicineAdapter = new ListOfMedicineAdapter(medicinePOJOList, getApplicationContext());
        listOfMedicineRV.setAdapter(listOfMedicineAdapter);

        FloatingActionButton fab = findViewById(R.id.addAMedicineFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddMedicineActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<MedicinePOJO> medicinePOJOList = MyApplication.myDatabaseAdapter.getData();
        listOfMedicineAdapter = new ListOfMedicineAdapter(medicinePOJOList, getApplicationContext());
        listOfMedicineRV.setAdapter(listOfMedicineAdapter);
    }
}