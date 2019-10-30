package com.example.donald.fedexapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TrackOrderActivity extends AppCompatActivity {
    private Button trackButton2;
    private EditText trackNum;
    private EditText trackNum2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);

        trackNum = (EditText)findViewById(R.id.trackNumber);
        trackNum2 = (EditText)findViewById(R.id.trackNumber2);

        trackButton2 = (Button)findViewById(R.id.trackButton2);
        try {
            trackButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openTrackOrderActivity2(trackNum.getText().toString(), trackNum2.getText().toString());
                }
            });
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void openTrackOrderActivity2(String trackNum, String trackNum2) {
        int num1, num2;
        if (trackNum.isEmpty())
            trackNum = "0";
        if (trackNum2.isEmpty())
            trackNum2 = "0";

        num1 = Integer.parseInt(trackNum);
        num2 = Integer.parseInt(trackNum2);

        Intent intent = new Intent(this, TrackOrderActivity2.class);
        intent.putExtra("tracknum", num1);
        intent.putExtra("tracknum2", num2);
        startActivity(intent);
    }
}
