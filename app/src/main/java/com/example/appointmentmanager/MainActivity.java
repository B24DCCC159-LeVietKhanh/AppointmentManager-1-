package com.example.appointmentmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView tvTotalCount, tvReminderCount;
    private ListView listView;
    private Button btnAdd;
    private DatabaseHelper dbHelper;
    private AppointmentAdapter adapter;
    private List<Appointment> appointmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        tvTotalCount = findViewById(R.id.tvTotalCount);
        tvReminderCount = findViewById(R.id.tvReminderCount);
        listView = findViewById(R.id.listViewAppointments);
        btnAdd = findViewById(R.id.btnAddAppointment);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddAppointmentActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Appointment a = appointmentList.get(position);
                Intent intent = new Intent(MainActivity.this, AppointmentDetailActivity.class);
                intent.putExtra("appointment_id", a.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        appointmentList = dbHelper.getAllAppointments();
        adapter = new AppointmentAdapter(this, appointmentList);
        listView.setAdapter(adapter);

        int total = dbHelper.countAll();
        int reminders = dbHelper.countReminders();
        tvTotalCount.setText(String.valueOf(total));
        tvReminderCount.setText(String.valueOf(reminders));
    }
}
