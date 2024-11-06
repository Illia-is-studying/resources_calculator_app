package com.example.resources_calculator_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.resources_calculator_app.Models.ColorsEnum;
import com.example.resources_calculator_app.Models.StylesType;
import com.example.resources_calculator_app.Services.SetStyleService;

public class SettingActivity extends AppCompatActivity {
    private StylesType stylesType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);

        Button buttonDefault = findViewById(R.id.button_default);
        Button buttonMysterious = findViewById(R.id.button_mysterious);

        Intent intent = getIntent();
        stylesType = (StylesType) intent.getSerializableExtra("StylesType");

        if (stylesType != null) {
            ColorsEnum firstButtonColors = ColorsEnum.DEFAULT;

            if (stylesType.equals(StylesType.MYSTERIOUS)) {
                firstButtonColors = ColorsEnum.BLUE;
            }

            SetStyleService.setStylesByColor(this, firstButtonColors,
                    buttonDefault, buttonMysterious);
        }
    }

    public void setStyle(View view) {
        Button button = (Button) view;

        String buttonText = button.getText().toString();

        stylesType = StylesType.DEFAULT;

        if (buttonText.equals(getResources().getString(R.string.mysterious))) {
            stylesType = StylesType.MYSTERIOUS;
        }

        Intent intent = new Intent();
        intent.putExtra("StylesType", stylesType);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

}
