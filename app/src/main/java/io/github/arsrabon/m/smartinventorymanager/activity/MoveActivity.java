package io.github.arsrabon.m.smartinventorymanager.activity;

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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

import io.github.arsrabon.m.smartinventorymanager.R;
import io.github.arsrabon.m.smartinventorymanager.data_model.Location;
import io.github.arsrabon.m.smartinventorymanager.data_model.Move;
import io.github.arsrabon.m.smartinventorymanager.data_model.Product;
import io.github.arsrabon.m.smartinventorymanager.data_model.Product_Category;

public class MoveActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Toolbar toolbar;
    private Spinner category_Spinner;
    private Spinner items_Spinner;
    private EditText edit_itemsCount;
    private Spinner source_location_Spinner;
    private Spinner destination_location_Spinner;
    private ImageButton btn_add;
    private ImageButton btn_remove;
    private Button btn_move;

    private Product product;
    private Product_Category category;
    private Location source_location;
    private Location destination_location;
    private List<Product> productList;
    private List<Product_Category> categoryList;
    private List<Location> source_locationList;
    private List<Location> destination_locationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Move Item");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        Type type = new Type("Warehouse", 1);
//        Type type1 = new Type("Showroom", 2);
//        type.save();
//        type1.save();
//        List<Type> typeList = Type.listAll(Type.class);
//        Location location = new Location("Demo Warehouse", typeList.get(0), "Address,Demo");
//        Location location1 = new Location("Demo ShowRoom", typeList.get(1), "Address,Demo");
//        Location location2 = new Location("Demo Warehouse B", typeList.get(0), "Address,Demo");
//        location.save();
//        location1.save();
//        location2.save();

        items_Spinner = (Spinner) findViewById(R.id.spin_items);
        category_Spinner = (Spinner) findViewById(R.id.spin_categories);
        source_location_Spinner = (Spinner) findViewById(R.id.spin_sourceLocation);
        destination_location_Spinner = (Spinner) findViewById(R.id.spin_destinationLocation);
        edit_itemsCount = (EditText) findViewById(R.id.edit_itemCount);
        btn_add = (ImageButton) findViewById(R.id.btn_add);
        btn_remove = (ImageButton) findViewById(R.id.btn_remove);
        btn_move = (Button) findViewById(R.id.btn_moveItems);

        source_locationList = Location.listAll(Location.class);
        destination_locationList = Location.listAll(Location.class);

        categorySpinnerInit(category_Spinner);
        category_Spinner.setOnItemSelectedListener(this);
//        productSpinnerInit(items_Spinner);
        items_Spinner.setOnItemSelectedListener(this);
        source_location_Spinner.setOnItemSelectedListener(this);
        sourceLocationSpinnerInit(source_location_Spinner);
        destination_location_Spinner.setOnItemSelectedListener(this);
//        destinationLocationSpinnerInit(destination_location_Spinner);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count;
                if (edit_itemsCount.getText().toString().trim().equals("")) {
                    count = 0;
                } else {
                    count = Integer.parseInt(edit_itemsCount.getText().toString().trim());
                }
                if (count >= 0) {
                    count++;
                    edit_itemsCount.setText(String.valueOf(count));
                }
            }
        });
        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count;
                if (edit_itemsCount.getText().toString().trim().equals("")) {
                    count = 1;
                } else {
                    count = Integer.parseInt(edit_itemsCount.getText().toString().trim());
                }
                if (count > 0) {
                    count--;
                    edit_itemsCount.setText(String.valueOf(count));
                } else {
                    Toast.makeText(MoveActivity.this, "Item count can't be a negetive number.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count;
                if (edit_itemsCount.getText().toString().trim().equals("")) {
                    count = 1;
                } else {
                    count = Integer.parseInt(edit_itemsCount.getText().toString().trim());
                }
                if (category != null && product != null && source_location != null && destination_location != null) {
                    Move move = new Move(product, count, source_location, destination_location);
                    move.save();
                    Toast.makeText(MoveActivity.this, "Successfully Moved item from " + source_location + " to " + destination_location, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MoveActivity.this, "Something Went wrong.", Toast.LENGTH_SHORT).show();
                    Logger.d(category);
                    Logger.d(product);
                    Logger.d(source_location);
                    Logger.d(destination_location);
                }
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

    public void sourceLocationSpinnerInit(Spinner sourceLocation_Spinner) {
        source_locationList = Location.listAll(Location.class);
        source_locationList.remove(destination_location);
        ArrayAdapter<Location> locationArrayAdapter = new ArrayAdapter<Location>(this, R.layout.spinner_item, source_locationList);
        locationArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceLocation_Spinner.setAdapter(locationArrayAdapter);
    }

    public void destinationLocationSpinnerInit(Spinner destinationLocation_Spinner) {

        destination_locationList = Location.listAll(Location.class);
        destination_locationList.remove(source_location);
        ArrayAdapter<Location> locationArrayAdapter = new ArrayAdapter<Location>(this, R.layout.spinner_item, source_locationList);
        locationArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        destinationLocation_Spinner.setAdapter(locationArrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.move_menu, menu);
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
            case R.id.add_moveSpace:
                Toast.makeText(this, "New item.", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int rid = parent.getId();
        switch (rid) {
            case R.id.spin_items:
                product = productList.get(position);
                break;
            case R.id.spin_categories:
                category = categoryList.get(position);
                productSpinnerInit(items_Spinner);
                break;
            case R.id.spin_sourceLocation:
                source_location = source_locationList.get(position);
                destinationLocationSpinnerInit(destination_location_Spinner);
                break;
            case R.id.spin_destinationLocation:
                destination_location = destination_locationList.get(position);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        int rid = parent.getId();
        switch (rid) {
            case R.id.spin_items:
                product = productList.get(0);
                break;
            case R.id.spin_categories:
                category = categoryList.get(0);
                productSpinnerInit(items_Spinner);
                break;
            case R.id.spin_sourceLocation:
                source_location = source_locationList.get(0);
                destinationLocationSpinnerInit(destination_location_Spinner);
                break;
            case R.id.spin_destinationLocation:
                destination_location = destination_locationList.get(0);
                break;
        }
    }
}
