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
import io.github.arsrabon.m.smartinventorymanager.data_model.Product;
import io.github.arsrabon.m.smartinventorymanager.data_model.Product_Category;

public class CheckInActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {

    private Toolbar toolbar;
    private EditText edt_ItemCode;
    private Spinner category_spinner;
    private Spinner product_spinner;
    private ImageButton btn_barcodeScanner;
    private ImageButton btn_datePicker;
    private TextView txt_date;
    private DigitalClock digitalClock;

    private List<Product> productList;
    private Product product;
    private List<Product_Category> categoryList;
    private Product_Category category;

    //qr code scanner object
    private IntentIntegrator qrScan;

    //Simple date
    Date date;
    SimpleDateFormat simpleDateFormat;
    private EditText edit_ShortCode;

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
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        //intializing scan object
        qrScan = new IntentIntegrator(this);

        edt_ItemCode = (EditText) findViewById(R.id.edt_itemCode);
        txt_date = (TextView) findViewById(R.id.txt_date);
        digitalClock = (DigitalClock) findViewById(R.id.digitalClock);
        btn_barcodeScanner = (ImageButton) findViewById(R.id.btn_barcodeScanner);
        btn_datePicker = (ImageButton) findViewById(R.id.btn_datePicker);
        category_spinner = (Spinner) findViewById(R.id.spin_categories);
        product_spinner = (Spinner) findViewById(R.id.spin_items);

        txt_date.setText("Date&Time: " + simpleDateFormat.format(date));

        //loads data into spinner view
        categorySpinnerInit(category_spinner);
        category_spinner.setOnItemSelectedListener(this);

        product_spinner.setOnItemSelectedListener(this);

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
                        (DatePickerDialog.OnDateSetListener) CheckInActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setVersion(DatePickerDialog.Version.VERSION_2);
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });
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
        final Spinner category_Spinner = (Spinner) dialog.findViewById(R.id.spin_categories);
        final EditText edit_itemName = (EditText) dialog.findViewById(R.id.edit_itemName);
        final EditText edit_itemCode = (EditText) dialog.findViewById(R.id.edit_itemCode);
        edit_ShortCode = edit_itemCode;
        final EditText edit_itemDescription = (EditText) dialog.findViewById(R.id.edit_itemDescription);
        final EditText edit_itemQuantity = (EditText) dialog.findViewById(R.id.edit_itemQuantity);
        final EditText edit_itemPrice = (EditText) dialog.findViewById(R.id.edit_itemPrice);
        categorySpinnerInit(category_Spinner);
        category_Spinner.setOnItemSelectedListener(this);

        btn_addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edit_itemName.getText().toString();
                String code = edit_itemCode.getText().toString();
                String description = edit_itemDescription.getText().toString();
                int qty = Integer.parseInt(edit_itemQuantity.getText().toString().trim());
                double price = Double.parseDouble(edit_itemPrice.getText().toString().trim());
                if (!name.isEmpty() && !code.isEmpty() && (category != null)) {
                    Product product = new Product(name, description, code, qty, price, category);
                    product.save();
                    productSpinnerInit(product_spinner);
                    dialog.dismiss();
                    Toast.makeText(CheckInActivity.this, "New item has been added.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CheckInActivity.this, "Something Went wrong here.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return;
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
                Toast.makeText(this, category.toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.spin_items:
                Toast.makeText(this, "item Spinner", Toast.LENGTH_SHORT).show();
                product = productList.get(position);
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
                Toast.makeText(this, category.toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.spin_items:
                Toast.makeText(this, "item Spinner 1", Toast.LENGTH_SHORT).show();
                product = productList.get(0);
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

        String date = day + "-" + month + "-" + year;
        txt_date.setText("Date&Time: " + date);
    }
}
