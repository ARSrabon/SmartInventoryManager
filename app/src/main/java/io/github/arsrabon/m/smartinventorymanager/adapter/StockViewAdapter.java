package io.github.arsrabon.m.smartinventorymanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.github.arsrabon.m.smartinventorymanager.R;
import io.github.arsrabon.m.smartinventorymanager.activity.ViewItemDetailsActivity;
import io.github.arsrabon.m.smartinventorymanager.data_model.Product;

/**
 * Created by msrabon on 5/22/17.
 */

public class StockViewAdapter extends RecyclerView.Adapter<StockViewAdapter.StockViewHolder> {

    List<Product> productList;
    Context context;

    public StockViewAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @Override
    public StockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_stock, parent, false);
        StockViewHolder viewHolder = new StockViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StockViewHolder holder, int position) {
        final Product product = productList.get(position);
        Log.d("position: ", String.valueOf(position));
        holder.txt_itemName.setText(product.getName());
        holder.txt_itemCategory.setText(product.getProduct_category().getCategoryName());
        holder.txt_itemQuantity.setText("Qty: " + String.valueOf(product.getQuantity()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewItemDetailsActivity.class);
                intent.putExtra("item_id", product.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("getItemCount: ", String.valueOf(productList.size()));
        return productList.size();
    }

    public class StockViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView txt_itemName;
        TextView txt_itemCategory;
        TextView txt_itemQuantity;

        public StockViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.stockCardView);
            txt_itemName = (TextView) itemView.findViewById(R.id.txt_item_name);
            txt_itemCategory = (TextView) itemView.findViewById(R.id.txt_item_category);
            txt_itemQuantity = (TextView) itemView.findViewById(R.id.txt_item_quantity);
        }
    }
}
