package io.github.arsrabon.m.smartinventorymanager.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import io.github.arsrabon.m.smartinventorymanager.R;

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
    private Button btn_Sales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Inventory Manager");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_checkIn = (Button) findViewById(R.id.btn_checkIn);
        btn_checkOut = (Button) findViewById(R.id.btn_checkOut);
        btn_move = (Button) findViewById(R.id.btn_move);
        btn_stock = (Button) findViewById(R.id.btn_Stock);
        btn_Analytics = (Button) findViewById(R.id.btn_analytics);
        btn_transactions = (Button) findViewById(R.id.btn_transaction);
        btn_buyer = (Button) findViewById(R.id.btn_buyer);
        btn_vendor = (Button) findViewById(R.id.btn_vendor);
        btn_Sales = (Button) findViewById(R.id.btn_Sales);

        btn_checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CheckInActivity.class);
                startActivity(intent);
            }
        });

        btn_checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CheckOutActivity.class);
                startActivity(intent);
            }
        });

        btn_transactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TransactionsActivity.class);
                startActivity(intent);
            }
        });

        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MoveActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Toast.makeText(this, "Home arrow btn is pressed.", Toast.LENGTH_SHORT).show();
//            onBackPressed();  return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
