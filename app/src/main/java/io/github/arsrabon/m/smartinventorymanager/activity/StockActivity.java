package io.github.arsrabon.m.smartinventorymanager.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import io.github.arsrabon.m.smartinventorymanager.data_model.Product;
import io.github.arsrabon.m.smartinventorymanager.data_model.Product_Category;

public class StockActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView stock_RecyclerView;
    private List<Product_Category> categoryList;
    private Product_Category category;

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
        }
        return super.onOptionsItemSelected(item);
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
        final EditText edit_itemCode = (EditText) dialog.findViewById(R.id.edit_itemName);
        final EditText edit_itemDescription = (EditText) dialog.findViewById(R.id.edit_itemName);
        final EditText edit_itemQuantity = (EditText) dialog.findViewById(R.id.edit_itemName);
        final EditText edit_itemPrice = (EditText) dialog.findViewById(R.id.edit_itemName);

        categorySpinnerInit(category_Spinner);
        Spinner.setOnItemClickListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = categoryList.get(position);
                edit_itemCode.setText(category.getShortCode());
                Toast.makeText(StockActivity.this, categoryList.get(position).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                category = categoryList.get(0);
                edit_itemCode.setText(category.getShortCode());
            }
        });

        btn_addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edit_itemName.getText().toString();
                String code = edit_itemCode.getText().toString();
                String description = edit_itemDescription.getText().toString();
                int qty = Integer.parseInt(edit_itemQuantity.getText().toString().trim());
                double price = Double.parseDouble(edit_itemPrice.getText().toString().trim());
                if (name.isEmpty() && code.isEmpty()) {
                    Product product = new Product(name, description, code, qty, price, category);
                    product.save();
                    dialog.dismiss();
                    Toast.makeText(StockActivity.this, "New item has been added.", Toast.LENGTH_SHORT).show();
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
}
