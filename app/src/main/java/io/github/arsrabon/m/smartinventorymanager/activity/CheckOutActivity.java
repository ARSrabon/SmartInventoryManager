package io.github.arsrabon.m.smartinventorymanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.github.arsrabon.m.smartinventorymanager.R;
import io.github.arsrabon.m.smartinventorymanager.data_model.Check_In;
import io.github.arsrabon.m.smartinventorymanager.data_model.Check_Out;
import io.github.arsrabon.m.smartinventorymanager.data_model.Product;
import io.github.arsrabon.m.smartinventorymanager.data_model.Product_Category;
import io.github.arsrabon.m.smartinventorymanager.data_model.Vendor;

public class CheckOutActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {

    private Toolbar toolbar;
    private EditText edit_checkInOrderID;
    private EditText edit_itemUnitePrice;
    private EditText edit_itemQuantity;
    private EditText edit_checkInDEscription;
    private Spinner category_spinner;
    private Spinner product_spinner;
    private Spinner vendor_Spinner;
    private ImageButton btn_barcodeScanner;
    private ImageButton btn_datePicker;
    private Button btn_addCheckOut;
    private TextView txt_date;
    private DigitalClock digitalClock;

    private List<Product> productList;
    private Product product;
    private List<Product_Category> categoryList;
    private Product_Category category;
    private List<Vendor> vendorList;
    private Vendor vendor;
    private String str_date;

    //qr code scanner object
    private IntentIntegrator qrScan;

    //Simple date
    Date date;
    SimpleDateFormat simpleDateFormat;
    private EditText edit_ShortCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Check Out");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // initiate date format
        date = new Date();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        //intializing scan object
        qrScan = new IntentIntegrator(this);

        edit_checkInOrderID = (EditText) findViewById(R.id.edit_checkinOrderid);
        edit_checkInDEscription = (EditText) findViewById(R.id.edit_checkInDescription);
        edit_itemQuantity = (EditText) findViewById(R.id.edit_quantity);
        edit_itemUnitePrice = (EditText) findViewById(R.id.edit_unitPrice);
        txt_date = (TextView) findViewById(R.id.txt_date);
        digitalClock = (DigitalClock) findViewById(R.id.digitalClock);
        btn_barcodeScanner = (ImageButton) findViewById(R.id.btn_barcodeScanner);
        btn_datePicker = (ImageButton) findViewById(R.id.btn_datePicker);
        btn_addCheckOut = (Button) findViewById(R.id.btn_checkOut);
        category_spinner = (Spinner) findViewById(R.id.spin_categories);
        product_spinner = (Spinner) findViewById(R.id.spin_items);
        vendor_Spinner = (Spinner) findViewById(R.id.spin_vendor);

        str_date = simpleDateFormat.format(date);
        txt_date.setText("Date&Time: " + str_date);

        //loads data into spinner view
        categorySpinnerInit(category_spinner);
        category_spinner.setOnItemSelectedListener(this);

        product_spinner.setOnItemSelectedListener(this);

        vendorSpinnerInit(vendor_Spinner);
        vendor_Spinner.setOnItemSelectedListener(this);

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

        btn_datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        (DatePickerDialog.OnDateSetListener) CheckOutActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setVersion(DatePickerDialog.Version.VERSION_2);
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        btn_addCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = edit_checkInOrderID.getText().toString();
                String description = edit_checkInDEscription.getText().toString();
                int qty = Integer.parseInt(edit_itemQuantity.getText().toString().trim());
                double price = Double.parseDouble(edit_itemUnitePrice.getText().toString().trim());
                if (!(code.isEmpty() && description.isEmpty()) && qty > 0 && price > 0.0) {
                    Check_Out check_out = new Check_Out(product, description, code, price, qty, vendor, str_date);
                    check_out.save();
                    onBackPressed();
                    finish();
                    Toast.makeText(CheckOutActivity.this, "Successfully Checked In Item:" + product.getName(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CheckOutActivity.this, "Please fill up all the fields.", Toast.LENGTH_SHORT).show();
                }
//                Toast.makeText(CheckInActivity.this, "Hello", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void vendorSpinnerInit(Spinner vendor_spinner) {
        vendorList = Vendor.listAll(Vendor.class);
        ArrayAdapter<Vendor> vendorArrayAdapter = new ArrayAdapter<Vendor>(this, R.layout.spinner_item, vendorList);
        vendorArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vendor_spinner.setAdapter(vendorArrayAdapter);
    }

    public void categorySpinnerInit(Spinner category_spinner) {
        categoryList = Product_Category.listAll(Product_Category.class);
        ArrayAdapter<Product_Category> categoryArrayAdapter = new ArrayAdapter<Product_Category>(this, R.layout.spinner_item, categoryList);
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner.setAdapter(categoryArrayAdapter);
    }

    public void productSpinnerInit(Spinner product_spinner) {

        productList = Select.from(Product.class).where(Condition.prop("productcategory").eq(category.getId())).list();
//        Log.d("productSpinnerInit: ", String.valueOf(productList.size()));
        ArrayAdapter<Product> productArrayAdapter = new ArrayAdapter<Product>(this, R.layout.spinner_item, productList);
        productArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        product_spinner.setAdapter(productArrayAdapter);
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
                    edit_checkInOrderID.setText(result.getContents().toString());
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
                onBackPressed();
//                Toast.makeText(this, "Home arrow btn is pressed.", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {
            case R.id.spin_categories:
                category = categoryList.get(position);
                productSpinnerInit(product_spinner);
                if (edit_ShortCode != null) {
                    edit_ShortCode.setText(category.getShortCode() + "_");
                }
//                Toast.makeText(this, category.toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.spin_items:
//                Toast.makeText(this, "item Spinner", Toast.LENGTH_SHORT).show();
                product = productList.get(position);
                break;
            case R.id.spin_vendor:
                vendor = vendorList.get(position);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        switch (parent.getId()) {
            case R.id.spin_categories:
                category = categoryList.get(0);
                productSpinnerInit(product_spinner);
                if (edit_ShortCode != null) {
                    edit_ShortCode.setText(category.getShortCode() + "_");
                }
//                Toast.makeText(this, category.toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.spin_items:
//                Toast.makeText(this, "item Spinner 1", Toast.LENGTH_SHORT).show();
                product = productList.get(0);
                break;
            case R.id.spin_vendor:
                vendor = vendorList.get(0);
                break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String month;
        String day;
        if ((monthOfYear + 1) < 10) {
            month = "0" + (monthOfYear + 1);
        } else {
            month = String.valueOf((monthOfYear + 1));
        }

        if ((dayOfMonth + 1) < 10) {
            day = "0" + (dayOfMonth);
        } else {
            day = String.valueOf((dayOfMonth));
        }


        str_date = day + "-" + month + "-" + year;
        txt_date.setText("Date&Time: " + str_date);
    }
}
