package com.example.donald.fedexapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private FedExDatabase db;
    private Button addButton;
    private Button trackButton;
    private Button listButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new FedExDatabase(this);

        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddOrderActivity();
            }
        });

        trackButton = (Button) findViewById(R.id.trackButton);
        trackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTrackOrderActivity();
            }
        });

        listButton = (Button) findViewById(R.id.listButton);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOrderListActivity();
            }
        });
    }

    public void openAddOrderActivity() {
        int trackNum = db.addOrder();
        Intent intent = new Intent(this, AddOrderActivity.class);
        intent.putExtra("trackNumber", trackNum);
        startActivity(intent);
    }

    public void openTrackOrderActivity() {
        Intent intent = new Intent(this, TrackOrderActivity.class);
        startActivity(intent);
    }

    public void openOrderListActivity() {
        Intent intent = new Intent(this, OrderListActivity.class);
        startActivity(intent);
    }
}
