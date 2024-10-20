package com.android.labmovilesg4;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

public class EditTaskActivity extends AppCompatActivity {

    EditText edtTitulo, edtDescripcion, edtFecha, edtHora;
    Button btnGuardar, btnFecha, btnHora;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        edtTitulo = findViewById(R.id.edtTitulo);
        edtDescripcion = findViewById(R.id.edtDescripcion);
        edtFecha = findViewById(R.id.edtFecha);
        edtHora = findViewById(R.id.edtHora);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnFecha = findViewById(R.id.btnFecha);
        btnHora = findViewById(R.id.btnHora);

        Intent intent = getIntent();
        String titulo = intent.getStringExtra("titulo");
        String descripcion = intent.getStringExtra("descripcion");
        String fecha = intent.getStringExtra("fecha");
        String hora = intent.getStringExtra("hora");
        position = intent.getIntExtra("position", -1);

        edtTitulo.setText(titulo);
        edtDescripcion.setText(descripcion);
        edtFecha.setText(fecha);
        edtHora.setText(hora);

        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditTaskActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String fechaSelect = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year);
                                edtFecha.setText(fechaSelect);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        btnHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(EditTaskActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String selectHora = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                                edtHora.setText(selectHora);
                            }
                        }, hour, minute, true);
                timePickerDialog.show();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nuevoTitulo = edtTitulo.getText().toString().trim();
                String nuevaDescripcion = edtDescripcion.getText().toString().trim();
                String nuevaFecha = edtFecha.getText().toString().trim();
                String nuevaHora = edtHora.getText().toString().trim();

                if (nuevoTitulo.isEmpty() || nuevaDescripcion.isEmpty() || nuevaFecha.isEmpty() || nuevaHora.isEmpty()) {
                    Toast.makeText(EditTaskActivity.this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("titulo", nuevoTitulo);
                    resultIntent.putExtra("descripcion", nuevaDescripcion);
                    resultIntent.putExtra("fecha", nuevaFecha);
                    resultIntent.putExtra("hora", nuevaHora);
                    resultIntent.putExtra("position", position);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });
    }
}
