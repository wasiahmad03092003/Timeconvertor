package com.example.timeconvertor;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText valueEditText;
    private Spinner fromUnitSpinner;
    private Spinner toUnitSpinner;
    private Button convertButton;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        valueEditText = findViewById(R.id.valueEditText);
        fromUnitSpinner = findViewById(R.id.fromUnitSpinner);
        toUnitSpinner = findViewById(R.id.toUnitSpinner);
        convertButton = findViewById(R.id.convertButton);
        resultTextView = findViewById(R.id.resultTextView);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.time_units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromUnitSpinner.setAdapter(adapter);
        toUnitSpinner.setAdapter(adapter);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertTime();
            }
        });
    }

    private void convertTime() {
        String valueStr = valueEditText.getText().toString();
        if (valueStr.isEmpty()) {
            resultTextView.setText("Please enter a value.");
            return;
        }

        double value = Double.parseDouble(valueStr);
        String fromUnit = fromUnitSpinner.getSelectedItem().toString();
        String toUnit = toUnitSpinner.getSelectedItem().toString();

        double convertedValue = convert(value, fromUnit, toUnit);
        displayResult(convertedValue, toUnit);
    }

    private double convert(double value, String fromUnit, String toUnit) {
        // Define conversion factors for various time units
        double secondsToMinutes = 0.0166667;
        double secondsToHours = 0.000277778;
        double minutesToSeconds = 60;
        double minutesToHours = 0.0166667;
        double hoursToSeconds = 3600;
        double hoursToMinutes = 60;

        if (fromUnit.equals("Seconds") && toUnit.equals("Minutes")) {
            return value * secondsToMinutes;
        } else if (fromUnit.equals("Seconds") && toUnit.equals("Hours")) {
            return value * secondsToHours;
        } else if (fromUnit.equals("Minutes") && toUnit.equals("Seconds")) {
            return value * minutesToSeconds;
        } else if (fromUnit.equals("Minutes") && toUnit.equals("Hours")) {
            return value * minutesToHours;
        } else if (fromUnit.equals("Hours") && toUnit.equals("Seconds")) {
            return value * hoursToSeconds;
        } else if (fromUnit.equals("Hours") && toUnit.equals("Minutes")) {
            return value * hoursToMinutes;
        } else {
            return value; // Default to no conversion
        }
    }

    private void displayResult(double convertedValue, String toUnit) {
        DecimalFormat df = new DecimalFormat("#.##");
        String result = df.format(convertedValue) + " " + toUnit;
        resultTextView.setText(result);
    }
}
