package io.github.arsrabon.m.smartinventorymanager.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import io.github.arsrabon.m.smartinventorymanager.R;

public class BuyerActivity extends AppCompatActivity {

    private Toolbar toolbar;

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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
//                Toast.makeText(this, "Home arrow btn is pressed.", Toast.LENGTH_SHORT).show();
                onBackPressed();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
