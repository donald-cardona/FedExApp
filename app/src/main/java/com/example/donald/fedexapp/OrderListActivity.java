package com.example.donald.fedexapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OrderListActivity extends AppCompatActivity {
    private Button ok;
    private TextView info;
    private FedExDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        db = new FedExDatabase(this);

        ok = (Button)findViewById(R.id.menubutton);
        info = (TextView)findViewById(R.id.orderList);
        try {
            info.setText(db.printDatabase());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
