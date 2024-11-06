package com.example.resources_calculator_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.resources_calculator_app.Models.ColorsEnum;
import com.example.resources_calculator_app.Models.StylesType;
import com.example.resources_calculator_app.Services.CalculationService;
import com.example.resources_calculator_app.Services.SetStyleService;

public class MainActivity extends AppCompatActivity {
    private StylesType stylesType;

    private ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                stylesType = (StylesType) data.getSerializableExtra("StylesType");

                                setStylesForButtons();
                            }
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
    }

    public void goToSetting(View view) {
        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
        intent.putExtra("StylesType", stylesType);
        activityResultLauncher.launch(intent);
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

    public void setStylesForButtons() {
        Button buttonClear = findViewById(R.id.button_clear);
        Button buttonPlusMinus = findViewById(R.id.button_plus_minus);
        Button buttonPercent = findViewById(R.id.button_percent);
        Button buttonPoint = findViewById(R.id.button_point);
        Button button1 = findViewById(R.id.button_1);
        Button button2 = findViewById(R.id.button_2);
        Button button3 = findViewById(R.id.button_3);
        Button button4 = findViewById(R.id.button_4);
        Button button5 = findViewById(R.id.button_5);
        Button button6 = findViewById(R.id.button_6);
        Button button7 = findViewById(R.id.button_7);
        Button button8 = findViewById(R.id.button_8);
        Button button9 = findViewById(R.id.button_9);
        Button button0 = findViewById(R.id.button_0);

        Button buttonPlus = findViewById(R.id.button_plus);
        Button buttonMinus = findViewById(R.id.button_minus);
        Button buttonMultiply = findViewById(R.id.button_multiply);
        Button buttonDivide = findViewById(R.id.button_divide);
        Button buttonEquals = findViewById(R.id.button_equals);
        Button buttonSetting = findViewById(R.id.button_change_style);

        if (stylesType != null) {
            ColorsEnum firstButtonColors = ColorsEnum.DEFAULT;
            ColorsEnum secondButtonColors = ColorsEnum.RED;

            if (stylesType.equals(StylesType.MYSTERIOUS)) {
                firstButtonColors = ColorsEnum.BLUE;
                secondButtonColors = ColorsEnum.GREEN;
            }

            SetStyleService.setStylesByColor(this, firstButtonColors,
                    button0, button1, button2, button3, button4, button5,
                    button6, button7, button8, button9, buttonClear,
                    buttonPercent, buttonPlusMinus, buttonPoint);

            SetStyleService.setStylesByColor(this,
                    secondButtonColors,
                    buttonPlus, buttonMinus, buttonMultiply, buttonDivide,
                    buttonEquals, buttonSetting);
        }
    }
}