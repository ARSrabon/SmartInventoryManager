package io.github.arsrabon.m.smartinventorymanager.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import java.util.List;

import io.github.arsrabon.m.smartinventorymanager.R;
import io.github.arsrabon.m.smartinventorymanager.adapter.VendorBuyerViewAdapter;
import io.github.arsrabon.m.smartinventorymanager.data_model.Buyer;

public class BuyerActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText edit_ShortCode;

    private RecyclerView buyerRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Buyers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buyerRecyclerview = (RecyclerView) findViewById(R.id.buyerVendorRecyclerview);
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL)
        buyerRecyclerview.setLayoutManager(linearLayoutManager);

        //List of Products
        List<Buyer> buyerList = Buyer.listAll(Buyer.class);
        Logger.d(buyerList);
        try {
            VendorBuyerViewAdapter vendorBuyerViewAdapter = new VendorBuyerViewAdapter(null, buyerList, BuyerActivity.this);
            buyerRecyclerview.setAdapter(vendorBuyerViewAdapter);
        } catch (Exception e) {
            e.printStackTrace();
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

        switch (id) {
            case android.R.id.home:
//                Toast.makeText(this, "Home arrow btn is pressed.", Toast.LENGTH_SHORT).show();
                onBackPressed();
                finish();
                break;
            case R.id.addNewItem:
//                Toast.makeText(this, "New item.", Toast.LENGTH_SHORT).show();
                insertNewBuyer();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertNewBuyer() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.insert_buyer_vendor);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.setTitle("New Buyer");

        //fixing an issue where dialogue was shrinked in bigger displays.
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();// this will show the dialoge.

        final EditText edit_name = (EditText) dialog.findViewById(R.id.edit_bv_name);
        final EditText edit_company = (EditText) dialog.findViewById(R.id.edit_bv_company);
        final EditText edit_email = (EditText) dialog.findViewById(R.id.edit_bv_email);
        final EditText edit_contact_personal = (EditText) dialog.findViewById(R.id.edit_bv_contact_personal);
        final EditText edit_contact_office = (EditText) dialog.findViewById(R.id.edit_bv_contact_office);
        final EditText edit_address = (EditText) dialog.findViewById(R.id.edit_bv_address);
        Button btn_insertBuyer = (Button) dialog.findViewById(R.id.btn_add_buyer_vendor);
        btn_insertBuyer.setText("Add Buyer");

        btn_insertBuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edit_name.getText().toString();
                String email = edit_email.getText().toString();
                String company = edit_company.getText().toString();
                String con_personal = edit_contact_personal.getText().toString();
                String con_office = edit_contact_office.getText().toString();
                String address = edit_address.getText().toString();

                if (!(name.isEmpty() && email.isEmpty() && company.isEmpty() && con_office.isEmpty()
                        && con_personal.isEmpty() && address.isEmpty())) {
                    Buyer buyer = new Buyer(name, company, email, con_personal, con_office, address);
                    buyer.save();
                    initRecyclerView();
                    dialog.dismiss();
                    Toast.makeText(BuyerActivity.this, "It's Ok.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BuyerActivity.this, "Please fillup all the informations.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
