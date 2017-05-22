package io.github.arsrabon.m.smartinventorymanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import io.github.arsrabon.m.smartinventorymanager.R;
import io.github.arsrabon.m.smartinventorymanager.data_model.Product;
import io.github.arsrabon.m.smartinventorymanager.data_model.Product_Category;

public class ViewItemDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Toolbar toolbar;
    private Intent intent;
    private EditText edit_itemName;
    private EditText edit_itemDescription;
    private EditText edit_itemCode;
    private EditText edit_itemPrice;
    private EditText edit_itemStock;
    private Spinner category_Spinner;

    private Button btn_update;
    private Button btn_sell;
    private Button btn_delete;

    private Product product;
    private Product_Category category;
    private List<Product_Category> categoryList;
    private EditText edit_ShortCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item_details);

        intent = getIntent();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Item Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        category_Spinner = (Spinner) findViewById(R.id.spin_categories);
        edit_itemName = (EditText) findViewById(R.id.edit_itemName);
        edit_itemCode = (EditText) findViewById(R.id.edit_itemCode);
        edit_ShortCode = edit_itemCode;
        edit_itemDescription = (EditText) findViewById(R.id.edit_itemDescription);
        edit_itemStock = (EditText) findViewById(R.id.edit_itemQuantity);
        edit_itemPrice = (EditText) findViewById(R.id.edit_itemPrice);

        btn_update = (Button) findViewById(R.id.btn_itemUpdate);
        btn_delete = (Button) findViewById(R.id.btn_itemDelete);
        btn_sell = (Button) findViewById(R.id.btn_itemSell);

        categorySpinnerInit(category_Spinner);
        category_Spinner.setOnItemSelectedListener(this);

        btn_update.setVisibility(View.GONE);

        long item_id = intent.getLongExtra("item_id", -1);

        if (item_id != -1) {
            product = Product.findById(Product.class, item_id);
            enableItemEdit(false);
            updateView();
        } else {
            Toast.makeText(this, "Something Went wrong.", Toast.LENGTH_SHORT).show();
        }

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_update.setVisibility(View.GONE);
                btn_delete.setVisibility(View.VISIBLE);
                btn_sell.setVisibility(View.VISIBLE);
                enableItemEdit(false);
                String name = edit_itemName.getText().toString();
                String code = edit_itemCode.getText().toString();
                String description = edit_itemDescription.getText().toString();
                int qty = Integer.parseInt(edit_itemStock.getText().toString().trim());
                double price = Double.parseDouble(edit_itemPrice.getText().toString().trim());
                if (!name.isEmpty() && !code.isEmpty() && (category != null)) {
                    product.setName(name);
                    product.setProduct_category(category);
                    product.setProductCode(code);
                    product.setDetails(description);
                    product.setQuantity(qty);
                    product.setCurrentPrice(price);
                    product.save();
                    Toast.makeText(ViewItemDetailsActivity.this, "Item information updated successfully.", Toast.LENGTH_SHORT).show();
                } else {
                    updateView();
                    Toast.makeText(ViewItemDetailsActivity.this, "Item Information update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product != null) {
                    product.delete();
                    onBackPressed();
                    Toast.makeText(ViewItemDetailsActivity.this, "Successfully deleted.", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ViewItemDetailsActivity.this, SalesActivity.class);
                intent1.putExtra("item_id", intent.getLongExtra("item_id", -1));
//                startActivity(intent1);
            }
        });


    }

    private void updateView() {
        edit_itemName.setText(product.getName());
        edit_itemCode.setText(product.getProductCode());
        edit_itemDescription.setText(product.getDetails());
        edit_itemStock.setText(String.valueOf(product.getQuantity()));
        edit_itemPrice.setText(String.valueOf(product.getCurrentPrice()));
        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).getId() == product.getProduct_category().getId()) {
                category_Spinner.setSelection(i);
            }
        }
    }

    public void categorySpinnerInit(Spinner category_spinner) {
        categoryList = Product_Category.listAll(Product_Category.class);
        ArrayAdapter<Product_Category> categoryArrayAdapter = new ArrayAdapter<Product_Category>(this, R.layout.spinner_item, categoryList);
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner.setAdapter(categoryArrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_item_menu, menu);
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
            case R.id.item_editor:
                enableItemEdit(true);
                btn_update.setVisibility(View.VISIBLE);
                btn_delete.setVisibility(View.INVISIBLE);
                btn_sell.setVisibility(View.INVISIBLE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category = categoryList.get(position);
        switch (parent.getId()) {
            case R.id.spin_categories:
                if (edit_ShortCode != null) {
                    edit_ShortCode.setText(category.getShortCode() + "_");
                }
                Toast.makeText(this, category.toString(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        category = categoryList.get(0);
        switch (parent.getId()) {
            case R.id.spin_categories:
                EditText editText = (EditText) findViewById(R.id.edit_itemCode);
                if (edit_ShortCode != null) {
                    edit_ShortCode.setText(category.getShortCode() + "_");
                }
                Toast.makeText(this, category.toString(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void enableItemEdit(boolean b) {
        edit_itemName.setEnabled(b);
        edit_itemCode.setEnabled(b);
        category_Spinner.setEnabled(b);
        edit_itemDescription.setEnabled(b);
        edit_itemPrice.setEnabled(b);
        edit_itemStock.setEnabled(b);
    }
}
