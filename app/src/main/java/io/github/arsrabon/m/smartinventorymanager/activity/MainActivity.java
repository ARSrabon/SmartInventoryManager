package io.github.arsrabon.m.smartinventorymanager.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import io.github.arsrabon.m.smartinventorymanager.R;
import io.github.arsrabon.m.smartinventorymanager.data_model.Product_Category;

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

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Inventory Manager");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences(getString(R.string.sharedPref), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getBoolean("logInEnabled", false)) {
//            Intent intent = new Intent(MainActivity.this)
        }

        if (sharedPreferences.getBoolean("basicDbInput", true)) {
            Product_Category category = new Product_Category("Default", "This is demo Default category.", "demo");
            category.save();
            editor.putBoolean("basicDbInput", false);
            editor.commit();
        }


        btn_checkIn = (Button) findViewById(R.id.btn_checkIn);
        btn_checkOut = (Button) findViewById(R.id.btn_checkOut);
        btn_move = (Button) findViewById(R.id.btn_move);
        btn_stock = (Button) findViewById(R.id.btn_Stock);
        btn_Analytics = (Button) findViewById(R.id.btn_analytics);
        btn_transactions = (Button) findViewById(R.id.btn_transaction);
        btn_buyer = (Button) findViewById(R.id.btn_buyer);
        btn_vendor = (Button) findViewById(R.id.btn_vendor);
        btn_Sales = (Button) findViewById(R.id.btn_Sales);

//        List<Product_Category> product_categories = new ArrayList<>();

//        product_categories.add(new Product_Category("Vegetable","Fresh vegetables from Farms","veg"));
//        product_categories.add(new Product_Category("Fruits","Fresh Fruits from Local Farms","fru"));
//
//        for (Product_Category category : product_categories){
//            category.save();
//        }

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

        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MoveActivity.class);
                startActivity(intent);
            }
        });

        btn_Sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SalesActivity.class);
                startActivity(intent);
            }
        });

        btn_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StockActivity.class);
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

        btn_buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BuyerActivity.class);
                startActivity(intent);
            }
        });

        btn_vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VendorActivity.class);
                startActivity(intent);
            }
        });

        btn_Analytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AnalyticsActivity.class);
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
        Intent intent;
        int id = item.getItemId();
        switch (id) {
            case R.id.backup:
                intent = new Intent(MainActivity.this, BackupManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.myAccount:
                intent = new Intent(MainActivity.this, MyAccountActivity.class);
                startActivity(intent);
                break;
            case R.id.settings:
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.about:
                intent = new Intent(MainActivity.this, AboutUsActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
