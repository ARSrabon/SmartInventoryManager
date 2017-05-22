package io.github.arsrabon.m.smartinventorymanager.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import io.github.arsrabon.m.smartinventorymanager.R;

public class BuyerActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText edit_ShortCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Buyers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        dialog.setContentView(R.layout.insert_item);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.setTitle("Add New Item");

        //fixing an issue where dialogue was shrinked in bigger displays.
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();// this will show the dialoge.

        return;
    }
}
