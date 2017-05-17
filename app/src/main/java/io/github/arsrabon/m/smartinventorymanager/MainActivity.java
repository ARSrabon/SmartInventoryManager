package io.github.arsrabon.m.smartinventorymanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button btn_checkIn;
    private Button btn_checkOut;
    private Button btn_move;
    private Button btn_stock;
    private Button btn_transactions;
    private Button btn_Analytics;
    private Button btn_buyer;
    private Button btn_vendor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Inventory Manager");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_checkIn = (Button) findViewById(R.id.btn_checkIn);

        btn_checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CheckInActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
