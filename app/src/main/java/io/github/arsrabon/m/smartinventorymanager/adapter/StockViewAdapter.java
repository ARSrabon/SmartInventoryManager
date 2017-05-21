package io.github.arsrabon.m.smartinventorymanager.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.arsrabon.m.smartinventorymanager.data_model.Product;

/**
 * Created by msrabon on 5/22/17.
 */

public class StockViewAdapter extends RecyclerView.Adapter<StockViewAdapter.StockViewHolder> {

    List<Product> productList;
    Context context;
    Activity activity;

    public StockViewAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
        this.activity = (Activity) context;
    }

    @Override
    public StockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(StockViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class StockViewHolder extends RecyclerView.ViewHolder {

        public StockViewHolder(View itemView) {
            super(itemView);
        }
    }
}
