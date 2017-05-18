package io.github.arsrabon.m.smartinventorymanager.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DigitalClock;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.github.arsrabon.m.smartinventorymanager.R;

public class CheckOutActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Date date;
    private SimpleDateFormat simpleDateFormat;

    private IntentIntegrator qrScan;

    private EditText edt_ItemCode;
    //    private TextView
    private ImageButton btn_barcodeScanner;
    private DigitalClock digitalClock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        setContentView(R.layout.activity_check_in);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Check Out");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // initiate date format
        date = new Date();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");

        //intializing scan object
        qrScan = new IntentIntegrator(this);

        edt_ItemCode = (EditText) findViewById(R.id.edt_itemCode);
        btn_barcodeScanner = (ImageButton) findViewById(R.id.btn_barcodeScanner);
        digitalClock = (DigitalClock) findViewById(R.id.digitalClock);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                Toast.makeText(this, "Home arrow btn is pressed.", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.addNewItem:
//                Toast.makeText(this, "New item.", Toast.LENGTH_SHORT).show();
//                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
