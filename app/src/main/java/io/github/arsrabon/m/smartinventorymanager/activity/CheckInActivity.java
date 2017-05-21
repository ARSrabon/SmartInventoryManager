package io.github.arsrabon.m.smartinventorymanager.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.github.arsrabon.m.smartinventorymanager.R;
import io.github.arsrabon.m.smartinventorymanager.data_model.Product;
import io.github.arsrabon.m.smartinventorymanager.data_model.Product_Category;

public class CheckInActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText edt_ItemCode;
    private Spinner category_spinner;
    private Spinner product_spinner;
    private ImageButton btn_barcodeScanner;
    private DigitalClock digitalClock;

    private List<Product> productList;
    private List<Product_Category> categoryList;

    //qr code scanner object
    private IntentIntegrator qrScan;

    //Simple date
    Date date;
    SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Check In");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // initiate date format
        date = new Date();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");

        //intializing scan object
        qrScan = new IntentIntegrator(this);

        edt_ItemCode = (EditText) findViewById(R.id.edt_itemCode);
        btn_barcodeScanner = (ImageButton) findViewById(R.id.btn_barcodeScanner);
        digitalClock = (DigitalClock) findViewById(R.id.digitalClock);
        category_spinner = (Spinner) findViewById(R.id.spin_categories);

        categorySpinnerInit();

        btn_barcodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initiating the qr code scan
                qrScan.setOrientationLocked(false);
                qrScan.setTimeout(20000);
//                qrScan.addExtra(Intents.Scan.INVERTED_SCAN, true);
                qrScan.setBeepEnabled(false);
                qrScan.initiateScan();
            }
        });
    }

    public void categorySpinnerInit() {
        categoryList = Product_Category.listAll(Product_Category.class);
        ArrayAdapter<Product_Category> categoryArrayAdapter = new ArrayAdapter<Product_Category>(this, R.layout.spinner_item, categoryList);
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner.setAdapter(categoryArrayAdapter);
    }

    public void productSpinnerInit() {
//        product_spinner = (Spinner) findViewById(R.id.spin_items);
//        productList = new Select().from(Product.class).execute();
//
//        ArrayAdapter<Product> productArrayAdapter = new ArrayAdapter<Product>(this, R.layout.spinner_item, productList);
//        productArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        product_spinner.setAdapter(productArrayAdapter);
    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    edt_ItemCode.setText(result.getContents().toString());
                    Toast.makeText(this, result.getContents().toString(), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checkin_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

//        if (id == android.R.id.home) {
//            Toast.makeText(this, "Home arrow btn is pressed.", Toast.LENGTH_SHORT).show();
////            onBackPressed();  return true;
//        }

        switch (id) {
            case android.R.id.home:
//                Toast.makeText(this, "Home arrow btn is pressed.", Toast.LENGTH_SHORT).show();
                onBackPressed();
                finish();
                break;
            case R.id.addNewItem:
//                Toast.makeText(this, "New item.", Toast.LENGTH_SHORT).show();
                insertNewItem();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertNewItem() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.insert_item);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.setTitle("Add New Item");

        //fixing an issue where dialogue was shrinked in bigger displays.
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();// this will show the dialoge.

        Button btn_addItem = (Button) dialog.findViewById(R.id.btn_addNewItem);

        btn_addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(CheckInActivity.this, "New item has been added.", Toast.LENGTH_SHORT).show();
            }
        });
        return;
    }
}
