package io.github.arsrabon.m.smartinventorymanager.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import io.github.arsrabon.m.smartinventorymanager.R;

public class SalesTransactionsFragment extends Fragment {

    private Spinner time_Spinner;
    private Spinner location_Spinner;
    private RecyclerView sales_RecyclerView;
    private View view;

    public SalesTransactionsFragment() {
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
        view = inflater.inflate(R.layout.sales_transactions, container, false);
        time_Spinner = (Spinner) view.findViewById(R.id.spin_viewHistory);
        location_Spinner = (Spinner) view.findViewById(R.id.spin_shopsLocation);
        sales_RecyclerView = (RecyclerView) view.findViewById(R.id.salesHistoryView);

        setRecyclerview(sales_RecyclerView);
        return view;

    }

    private void setRecyclerview(RecyclerView sales_recyclerView) {

    }

}
