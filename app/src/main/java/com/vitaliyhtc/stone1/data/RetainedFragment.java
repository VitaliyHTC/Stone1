package com.vitaliyhtc.stone1.data;

import android.app.Fragment;
import android.os.Bundle;

import com.vitaliyhtc.stone1.model.Product;

import java.util.List;

public class RetainedFragment extends Fragment {

    private List<Product> products;
    private Product product;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
