package com.example.appointmentmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AppointmentDetailActivity extends AppCompatActivity {
    private TextView tvTitle, tvLocation, tvDateTime, tvReminderStatus;
    private Switch switchReminder;
    private Button btnBack, btnDelete;
    private DatabaseHelper dbHelper;
    private int appointmentId;
    private Appointment appointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_detail);

        dbHelper = new DatabaseHelper(this);

        tvTitle = findViewById(R.id.tvDetailTitle);
        tvLocation = findViewById(R.id.tvDetailLocation);
        tvDateTime = findViewById(R.id.tvDetailDateTime);
        tvReminderStatus = findViewById(R.id.tvReminderStatus);
        switchReminder = findViewById(R.id.switchDetailReminder);
        btnBack = findViewById(R.id.btnBack);
        btnDelete = findViewById(R.id.btnDelete);

        appointmentId = getIntent().getIntExtra("appointment_id", -1);
        if (appointmentId == -1) {
            Toast.makeText(this, "Không tìm thấy lịch hẹn", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadAppointment();

        switchReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (appointment != null) {
                    dbHelper.updateReminder(appointmentId, isChecked);
                    appointment.setReminder(isChecked);
                    updateReminderStatusText(isChecked);
                    Toast.makeText(AppointmentDetailActivity.this,
                            isChecked ? "Đã bật nhắc nhở" : "Đã tắt nhắc nhở",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AppointmentDetailActivity.this)
                        .setTitle("Xác nhận xóa")
                        .setMessage("Bạn có chắc muốn xóa lịch hẹn này?")
                        .setPositiveButton("Xóa", (dialog, which) -> {
                            dbHelper.deleteAppointment(appointmentId);
                            Toast.makeText(AppointmentDetailActivity.this, "Đã xóa lịch hẹn", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
            }
        });
    }

    private void loadAppointment() {
        appointment = dbHelper.getAppointmentById(appointmentId);
        if (appointment == null) {
            Toast.makeText(this, "Không tìm thấy lịch hẹn", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tvTitle.setText(appointment.getTitle());
        tvLocation.setText(appointment.getLocation());
        tvDateTime.setText(appointment.getFormattedDateTime());
        switchReminder.setChecked(appointment.isReminder());
        updateReminderStatusText(appointment.isReminder());
    }

    private void updateReminderStatusText(boolean on) {
        tvReminderStatus.setText(on ? "Đang bật" : "Đang tắt");
    }
}
