package io.github.arsrabon.m.smartinventorymanager.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.arsrabon.m.smartinventorymanager.R;
import io.github.arsrabon.m.smartinventorymanager.adapter.StockViewAdapter;
import io.github.arsrabon.m.smartinventorymanager.data_model.Product;

public class StockTransactionsFragment extends Fragment {

    private View view;
    private RecyclerView stock_RecyclerView;
    private Context context;
    public StockTransactionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.stock_transactions, container, false);
        stock_RecyclerView = (RecyclerView) view.findViewById(R.id.stockHistoryView);
        setRecyclerView(stock_RecyclerView);
        return view;
    }

    private void setRecyclerView(RecyclerView stock_recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        //linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL)
        stock_RecyclerView.setLayoutManager(linearLayoutManager);

        //List of Products
        List<Product> productList = Product.listAll(Product.class);

        try {
            StockViewAdapter stockViewAdapter = new StockViewAdapter(productList,getActivity());
            stock_RecyclerView.setAdapter(stockViewAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
