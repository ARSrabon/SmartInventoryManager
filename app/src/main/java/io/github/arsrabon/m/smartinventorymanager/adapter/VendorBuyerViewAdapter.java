package io.github.arsrabon.m.smartinventorymanager.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.github.arsrabon.m.smartinventorymanager.R;
import io.github.arsrabon.m.smartinventorymanager.data_model.Buyer;
import io.github.arsrabon.m.smartinventorymanager.data_model.Vendor;

/**
 * Created by msrabon on 5/23/17.
 */

public class VendorBuyerViewAdapter extends RecyclerView.Adapter<VendorBuyerViewAdapter.VendorBuyerViewHolder> {

    private List<Vendor> vendorList;
    private List<Buyer> buyerList;
    private Context context;

    public VendorBuyerViewAdapter(List<Vendor> vendorList, List<Buyer> buyerList, Context context) {
        this.vendorList = vendorList;
        this.buyerList = buyerList;
        this.context = context;
    }

    @Override
    public VendorBuyerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_vendor_buyer, parent, false);
        VendorBuyerViewHolder viewHolder = new VendorBuyerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VendorBuyerViewHolder holder, int position) {
        if (vendorList == null) {
            Log.d("onBindViewHolder: ", "");
            holder.txt_name.setText(buyerList.get(position).getName());
            holder.txt_email.setText(buyerList.get(position).getEmail());
            holder.txt_contact_office.setText(buyerList.get(position).getContact_Office());
        } else {
            Log.d("onBindViewHolder: ", "");
            holder.txt_name.setText(vendorList.get(position).getName());
            holder.txt_email.setText(vendorList.get(position).getEmail());
            holder.txt_contact_office.setText(vendorList.get(position).getContact_Office());
        }


    }

    @Override
    public int getItemCount() {
        if (vendorList == null) {
            Log.d("getItemCount: buyers", String.valueOf(buyerList.size()));
            return buyerList.size();
        }
        Log.d("getItemCount: vendor", String.valueOf(vendorList.size()));
        return vendorList.size();
    }

    public class VendorBuyerViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView txt_name;
        private TextView txt_email;
        private TextView txt_contact_office;

        public VendorBuyerViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.vendor_buyer_cardview);
            txt_name = (TextView) itemView.findViewById(R.id.txt_vendorBuyerName);
            txt_email = (TextView) itemView.findViewById(R.id.txt_vendorBuyerEmail);
            txt_contact_office = (TextView) itemView.findViewById(R.id.txt_vendorBuyerContact_office);
        }
    }

}
