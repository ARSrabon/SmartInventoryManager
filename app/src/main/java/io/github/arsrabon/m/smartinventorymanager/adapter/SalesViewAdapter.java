package io.github.arsrabon.m.smartinventorymanager.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.arsrabon.m.smartinventorymanager.data_model.Sales;

/**
 * Created by msrabon on 6/14/17.
 */

public class SalesViewAdapter extends RecyclerView.Adapter<SalesViewAdapter.SalesViewHolder> {

    private List<Sales> salesList;
    private Context context;
    private Activity activity;

    public SalesViewAdapter(List<Sales> salesList, Context context) {
        this.salesList = salesList;
        this.context = context;
        this.activity = (Activity) context;
    }

    @Override
    public SalesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(SalesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return salesList.size();
    }

    static class SalesViewHolder extends RecyclerView.ViewHolder {

        public SalesViewHolder(View itemView) {
            super(itemView);
        }
    }
}
