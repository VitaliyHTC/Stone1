package com.vitaliyhtc.stone1.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vitaliyhtc.stone1.MainActivity;
import com.vitaliyhtc.stone1.ProductDetailsActivity;
import com.vitaliyhtc.stone1.R;
import com.vitaliyhtc.stone1.databinding.ItemRecyclerViewBinding;
import com.vitaliyhtc.stone1.model.Product;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private ItemRecyclerViewBinding binding;

    public ProductViewHolder(View view){
        super(view);
        binding = DataBindingUtil.bind(view);
        view.setOnClickListener(this);
    }

    public void bind(Product product){
        binding.setProduct(product);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(v.getContext(), ProductDetailsActivity.class);
        intent.putExtra("productId", binding.getProduct().getId());
        intent.putExtra("productImageThumbUrl", binding.getProduct().getImageThumbUrl());

        Context context = v.getContext();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            if(context instanceof MainActivity){

                MainActivity mainActivity = (MainActivity) context;

                View imageView = v.findViewById(R.id.image_view_product_small);

                View decor = mainActivity.getWindow().getDecorView();
                View actionBar = decor.findViewById(R.id.action_bar_container);

                Pair<View, String> p0 = Pair.create(imageView, "productImage");
                Pair<View, String> p1 = Pair.create(actionBar, "action_bar_container");

                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(mainActivity, p0, p1);

                context.startActivity(intent, options.toBundle());
            } else {
                context.startActivity(intent);
            }
        } else {
            context.startActivity(intent);
        }
    }

}
