package com.example.appointmentmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

public class AddAppointmentActivity extends AppCompatActivity {
    private EditText edtTitle, edtLocation;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Switch switchReminder;
    private Button btnSave;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        dbHelper = new DatabaseHelper(this);

        edtTitle = findViewById(R.id.edtTitle);
        edtLocation = findViewById(R.id.edtLocation);
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        switchReminder = findViewById(R.id.switchReminder);
        btnSave = findViewById(R.id.btnSave);

        // Set TimePicker to spinner mode and 24-hour
        timePicker.setIs24HourView(true);

        // Set default date to today
        Calendar calendar = Calendar.getInstance();
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAppointment();
            }
        });
    }

    private void saveAppointment() {
        String title = edtTitle.getText().toString().trim();
        String location = edtLocation.getText().toString().trim();

        if (title.isEmpty()) {
            edtTitle.setError("Không được để trống tên lịch hẹn");
            return;
        }
        if (location.isEmpty()) {
            edtLocation.setError("Không được để trống địa điểm");
            return;
        }

        int year = datePicker.getYear();
        int month = datePicker.getMonth(); // 0-based
        int day = datePicker.getDayOfMonth();

        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        // Check if datetime is in the future
        Calendar selectedCal = Calendar.getInstance();
        selectedCal.set(year, month, day, hour, minute, 0);
        selectedCal.set(Calendar.MILLISECOND, 0);

        Calendar now = Calendar.getInstance();
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);

        if (selectedCal.before(now) || selectedCal.equals(now)) {
            Toast.makeText(this, "Thời điểm hẹn phải sau thời điểm hiện tại", Toast.LENGTH_SHORT).show();
            return;
        }

        // Format date as yyyy-MM-dd, time as HH:mm
        String dateDb = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, day);
        String timeDb = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
        boolean reminder = switchReminder.isChecked();

        Appointment appointment = new Appointment(title, location, dateDb, timeDb, reminder);
        long id = dbHelper.insertAppointment(appointment);

        if (id != -1) {
            Toast.makeText(this, "Đã lưu lịch hẹn thành công", Toast.LENGTH_SHORT).show();
            finish(); // Return to MainActivity
        } else {
            Toast.makeText(this, "Lỗi khi lưu lịch hẹn", Toast.LENGTH_SHORT).show();
        }
    }
}
