package com.example.asus.userinitialcircleview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserInitialCircleView circleView = findViewById(R.id.test_circle_view);
        circleView.setInitialText("OK");
        circleView.setInitialSize(400);
        circleView.setStrokeColor(Color.DKGRAY);
        circleView.setFillColor(Color.WHITE);
        circleView.setShadowColor(Color.GRAY);

    }
}