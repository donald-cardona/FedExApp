package com.example.donald.fedexapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TrackOrderActivity2 extends AppCompatActivity {
    private FedExDatabase db;
    private Button menuButton;
    private TextView order1;
    private TextView startloc1;
    private TextView currloc1;
    private String loc1;
    private String loc2;
    private TextView endloc1;
    private TextView deliver1;
    private TextView order2;
    private TextView startloc2;
    private TextView currloc2;
    private TextView endloc2;
    private TextView deliver2;
    private Handler handler1 = new Handler();
    private Handler handler2 = new Handler();

    private int trackNumber;
    private int trackNumber2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order2);
        db = new FedExDatabase(this);

        order1 = (TextView)findViewById(R.id.order1);
        startloc1 = (TextView)findViewById(R.id.startloc1);
        currloc1 = (TextView)findViewById(R.id.currloc1);
        endloc1 = (TextView)findViewById(R.id.endloc1);
        deliver1 = (TextView)findViewById(R.id.deliver1);

        order2 = (TextView)findViewById(R.id.order2);
        startloc2 = (TextView)findViewById(R.id.startloc2);
        currloc2 = (TextView)findViewById(R.id.currloc2);
        endloc2 = (TextView)findViewById(R.id.endloc2);
        deliver2 = (TextView)findViewById(R.id.deliver2);

        trackNumber = getIntent().getIntExtra("tracknum", 0);
        trackNumber2 = getIntent().getIntExtra("tracknum2", 0);

        try {
            Thread package1 = new Thread(new Runnable() {
                private FedExDistCenters distCenters = new FedExDistCenters();
                @Override
                public void run() {
                    String startloc;
                    final String currLoc;
                    String endloc;
                    int startIndex = 0;
                    int endIndex = 0;
                    Node node = null;
                    deliver1.setText("");


                    if (!db.orderExistCheck(trackNumber)) {
                        order1.setText("Track number does not exist!");
                        startloc1.setText("");
                        currloc1.setText("");
                        endloc1.setText("");
                        return;
                    }

                    Cursor result = db.getData(trackNumber);
                    result.moveToNext();
                    startloc = result.getString(4);
                    endloc = result.getString(5);
                    for (int i = 0; i < distCenters.size(); i++) {
                        if (distCenters.getNode(i).getLocation().equalsIgnoreCase(startloc)) {
                            startIndex = i;
                            distCenters.getNode(i).setDistance(0);
                        }
                        if (distCenters.getNode(i).getLocation().equalsIgnoreCase(endloc))
                            endIndex = i;
                    }
                    distCenters.setStack(distCenters.shortestPath(distCenters.getLocations(), distCenters.getStack(), 1, startIndex, endIndex));

                    order1.setText("Order #: " + String.valueOf(trackNumber));
                    startloc1.setText(startloc);
                    endloc1.setText(endloc);

                    while (!distCenters.getStack().isEmpty()) {
                        try {
                            node = distCenters.getStack().pop();
                            loc1 = node.getLocation();
                            handler1.post(new Runnable() {
                                @Override
                                public void run() {
                                    currloc1.setText(loc1);
                                }
                            });
                            Thread.sleep(3000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    handler1.post(new Runnable() {
                        @Override
                        public void run() {
                            deliver1.setText("Delivered!");
                        }
                    });
                }
            });
            package1.start();

            Thread package2 = new Thread(new Runnable() {
                private FedExDistCenters distCenters = new FedExDistCenters();
                @Override
                public void run() {
                    String startloc;
                    String endloc;
                    int startIndex = 0;
                    int endIndex = 0;
                    Node node = null;
                    deliver2.setText("");


                    if (!db.orderExistCheck(trackNumber2)) {
                        order2.setText("Track number does not exist!");
                        startloc2.setText("");
                        currloc2.setText("");
                        endloc2.setText("");
                        return;
                    }

                    Cursor result = db.getData(trackNumber2);
                    result.moveToNext();
                    startloc = result.getString(4);
                    endloc = result.getString(5);
                    for (int i = 0; i < distCenters.size(); i++) {
                        if (distCenters.getNode(i).getLocation().equalsIgnoreCase(startloc)) {
                            startIndex = i;
                            distCenters.getNode(i).setDistance(0);
                        }
                        if (distCenters.getNode(i).getLocation().equalsIgnoreCase(endloc))
                            endIndex = i;
                    }
                    distCenters.setStack(distCenters.shortestPath(distCenters.getLocations(), distCenters.getStack(), 1, startIndex, endIndex));

                    order2.setText("Order #: " + String.valueOf(trackNumber2));
                    startloc2.setText(startloc);
                    endloc2.setText(endloc);

                    while (!distCenters.getStack().isEmpty()) {
                        try {
                            node = distCenters.getStack().pop();
                            loc2 = node.getLocation();
                            handler2.post(new Runnable() {
                                @Override
                                public void run() {
                                    currloc2.setText(loc2);
                                }
                            });
                            Thread.sleep(3000);
                        }
                        catch(Exception e) {
                            e.printStackTrace();
                        }

                    }
                    handler2.post(new Runnable() {
                        @Override
                        public void run() {
                            deliver2.setText("Delivered!");
                        }
                    });
                }
            });

            package2.start();

            menuButton = (Button)findViewById(R.id.menubutton2);
            menuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openMainActivity();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
