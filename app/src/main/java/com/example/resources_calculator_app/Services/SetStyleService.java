package com.example.resources_calculator_app.Services;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Button;

import androidx.core.content.ContextCompat;

import com.example.resources_calculator_app.Models.ColorsEnum;
import com.example.resources_calculator_app.R;

public class SetStyleService {
    public static void setStylesByColor(Context context, ColorsEnum colorsEnum, Button... buttons) {
        if(colorsEnum.equals(ColorsEnum.DEFAULT)) {
            setDefaultColors(context, buttons);
        } else if(colorsEnum.equals(ColorsEnum.RED)) {
            setRedColors(context, buttons);
        } else if(colorsEnum.equals(ColorsEnum.BLUE)) {
            setBlueColors(context, buttons);
        } else if(colorsEnum.equals(ColorsEnum.GREEN)) {
            setGreenColors(context, buttons);
        }
    }

    public static void setDefaultColors(Context context, Button... buttons) {
        SetStyleService.setStyleForButtons(
                ContextCompat.getDrawable(context, R.drawable.default_button_selector),
                ContextCompat.getColor(context, R.color.dark_gray),
                buttons);
    }

    public static void setRedColors(Context context, Button... buttons) {
        SetStyleService.setStyleForButtons(
                ContextCompat.getDrawable(context, R.drawable.red_button_selector),
                ContextCompat.getColor(context, R.color.white),
                buttons);
    }

    public static void setBlueColors(Context context, Button... buttons) {
        SetStyleService.setStyleForButtons(
                ContextCompat.getDrawable(context, R.drawable.light_blue_button_selector),
                ContextCompat.getColor(context, R.color.darkest_green),
                buttons);
    }

    public static void setGreenColors(Context context, Button... buttons) {
        SetStyleService.setStyleForButtons(
                ContextCompat.getDrawable(context, R.drawable.green_button_selector),
                ContextCompat.getColor(context, R.color.darkest_green),
                buttons);
    }

    public static void setStyleForButtons(Drawable background, int fontColor, Button... buttons) {
        for (Button button : buttons) {
            button.setBackground(background);
            button.setTextColor(fontColor);
        }
    }
}
