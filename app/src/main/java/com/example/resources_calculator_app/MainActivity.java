package com.example.resources_calculator_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.resources_calculator_app.Services.CalculationService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
    }

    public void executeAction(View view) {
        TextView textView = findViewById(R.id.outputTextView);
        String textViewContent = textView.getText().toString();

        Button button = (Button) view;
        String buttonText = button.getText().toString();

        CalculationService calcService = new CalculationService(textViewContent, buttonText);
        calcService.calculateExpression();
        textViewContent = calcService.getContent();

        textView.setText(textViewContent);
    }
}