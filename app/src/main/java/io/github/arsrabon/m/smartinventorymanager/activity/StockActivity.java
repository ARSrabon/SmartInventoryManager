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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import io.github.arsrabon.m.smartinventorymanager.R;
import io.github.arsrabon.m.smartinventorymanager.adapter.StockViewAdapter;
import io.github.arsrabon.m.smartinventorymanager.data_model.Product;
import io.github.arsrabon.m.smartinventorymanager.data_model.Product_Category;

public class StockActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Toolbar toolbar;
    private RecyclerView stock_RecyclerView;
    private EditText edit_ShortCode;
    private List<Product_Category> categoryList;
    private Product_Category category;
    private boolean newItem = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Stock");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        stock_RecyclerView = (RecyclerView) findViewById(R.id.stockRecyclerview);
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL)
        stock_RecyclerView.setLayoutManager(linearLayoutManager);

        //List of Products
        List<Product> productList = Product.listAll(Product.class);

        try {
            StockViewAdapter stockViewAdapter = new StockViewAdapter(productList, StockActivity.this);
            stock_RecyclerView.setAdapter(stockViewAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.stock_menu, menu);
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
            case R.id.add_item:
                insertNewItem();
                break;
            case R.id.add_category:
                insertNewCategory();
                break;
            case R.id.update:
                initRecyclerView();
                break;
            case R.id.viewCategory:
//                viewCategories();
                Toast.makeText(this, "we are working on this portion of the app.", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void viewCategories() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.insert_category);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.setTitle("Categories");

        //fixing an issue where dialogue was shrinked in bigger displays.
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();// this will show the dialoge.
    }

    public void categorySpinnerInit(Spinner spinner) {
        categoryList = Product_Category.listAll(Product_Category.class);
        ArrayAdapter<Product_Category> categoryArrayAdapter = new ArrayAdapter<Product_Category>(this, R.layout.spinner_item, categoryList);
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(categoryArrayAdapter);
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
        newItem = true;
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
                    initRecyclerView();
                    newItem = false;
                    dialog.dismiss();
                    Toast.makeText(StockActivity.this, "New item has been added.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(StockActivity.this, "Something Went wrong here.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return;
    }

    private void insertNewCategory() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.insert_category);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.setTitle("Add New Category");

        //fixing an issue where dialogue was shrinked in bigger displays.
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();// this will show the dialoge.

        Button btn_addItem = (Button) dialog.findViewById(R.id.btn_addNewItem);
        final EditText edit_categoryName = (EditText) dialog.findViewById(R.id.edit_categoryName);
        final EditText edit_description = (EditText) dialog.findViewById(R.id.edit_description);
        final EditText edit_shortCode = (EditText) dialog.findViewById(R.id.edit_shortCode);

        btn_addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edit_categoryName.getText().toString();
                String description = edit_description.getText().toString();
                String shortCode = edit_shortCode.getText().toString();

                if (!name.isEmpty() && !description.isEmpty()) {
                    Product_Category category = new Product_Category(name, description, shortCode);
                    category.save();
                    dialog.dismiss();
                    Toast.makeText(StockActivity.this, "Successfully Added new cateegory.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(StockActivity.this, "Category Name Or Desccription Or Short Code Can't be Empty.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return;
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

    @Override
    protected void onRestart() {
        super.onRestart();
        initRecyclerView();
    }
}
