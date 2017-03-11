package com.vitaliyhtc.stone1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vitaliyhtc.stone1.R;
import com.vitaliyhtc.stone1.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private List<Product> mProducts;

    public ProductAdapter(List<Product> products){
        this.mProducts = products;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View statusContainer = inflater.inflate(R.layout.item_recycler_view, parent, false);

        return new ProductViewHolder(statusContainer);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position){
        Product status = mProducts.get(position);
        holder.bind(status);
    }

    @Override
    public int getItemCount(){
        return mProducts.size();
    }

    public void removeAt(int position) {
        mProducts.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mProducts.size());
    }

}
