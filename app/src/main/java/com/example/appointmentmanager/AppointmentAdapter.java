package com.example.appointmentmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AppointmentAdapter extends ArrayAdapter<Appointment> {
    private Context context;
    private List<Appointment> appointments;

    public AppointmentAdapter(Context context, List<Appointment> appointments) {
        super(context, 0, appointments);
        this.context = context;
        this.appointments = appointments;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_appointment, parent, false);
        }

        Appointment a = appointments.get(position);

        TextView tvTitle = convertView.findViewById(R.id.tvItemTitle);
        TextView tvLocation = convertView.findViewById(R.id.tvItemLocation);
        TextView tvDateTime = convertView.findViewById(R.id.tvItemDateTime);
        TextView tvReminder = convertView.findViewById(R.id.tvItemReminder);

        tvTitle.setText(a.getTitle());
        tvLocation.setText("Địa điểm: " + a.getLocation());
        tvDateTime.setText(a.getFormattedDateTime());
        tvReminder.setText(a.isReminder() ? "Có nhắc" : "Không nhắc");

        return convertView;
    }
}
