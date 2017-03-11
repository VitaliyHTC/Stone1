package com.vitaliyhtc.stone1.data;

import android.util.Log;
import android.widget.Toast;

import com.vitaliyhtc.stone1.Config;
import com.vitaliyhtc.stone1.MainActivity;
import com.vitaliyhtc.stone1.model.Product;
import com.vitaliyhtc.stone1.model.ProductsResult;
import com.vitaliyhtc.stone1.rest.ApiInterface;
import com.vitaliyhtc.stone1.rest.RetrofitApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsDataManager {

    private static final String LOG_TAG = ProductsDataManager.class.getSimpleName();

    private MainActivity mContext;

    private List<Product> mProductsResult = new ArrayList<>();

    public ProductsDataManager(MainActivity context) {
        this.mContext = context;
    }

    public void getProductsPage(int offset) {
        if (getNetworkAvailability()) {
            getProductsPageFromNetwork(offset);
        } else {
            mContext.onNoInternetConnection();
        }
    }

    private void onProductsPageLoaded(int offset, List<Product> products){
        if(products.isEmpty()){
            Toast.makeText(mContext, "Sorry. Looks like LCBO api temporarily unavailable.",
                    Toast.LENGTH_LONG).show();
        }
        mContext.onProductsListLoaded(products, offset);
    }

    private void getProductsPageFromNetwork(final int offset){
        mProductsResult.clear();

        ApiInterface apiService = RetrofitApiClient.getClient().create(ApiInterface.class);

        Call<ProductsResult> call = apiService.getProductsResult(offset, Config.PRODUCTS_PER_PAGE,
                Config.PRODUCTS_WHERE_NOT, Config.LCBO_API_KEY);

        call.enqueue(new Callback<ProductsResult>() {
            @Override
            public void onResponse(Call<ProductsResult>call, Response<ProductsResult> response) {

                if(response.isSuccessful()){
                    ProductsResult productsResult = response.body();
                    mProductsResult = productsResult.getResult();
                }else{
                    Log.e(LOG_TAG, "getProductsPageFromNetwork() - response problem.");
                }
                onProductsPageLoaded(offset, mProductsResult);
            }

            @Override
            public void onFailure(Call<ProductsResult>call, Throwable t) {
                // Log error here since request failed
                Log.e(LOG_TAG, t.toString());

                // Continue work.
                onProductsPageLoaded(offset, mProductsResult);
            }
        });

    }

    private boolean getNetworkAvailability() {
        return mContext.isNetworkAvailable();
    }

}
