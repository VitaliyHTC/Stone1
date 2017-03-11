package com.vitaliyhtc.stone1;

import android.app.FragmentManager;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.vitaliyhtc.stone1.activity.CoreActivity;
import com.vitaliyhtc.stone1.data.RetainedFragment;
import com.vitaliyhtc.stone1.databinding.ProductDetailsActivityBinding;
import com.vitaliyhtc.stone1.model.Product;
import com.vitaliyhtc.stone1.model.ProductResult;
import com.vitaliyhtc.stone1.rest.ApiInterface;
import com.vitaliyhtc.stone1.rest.RetrofitApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends CoreActivity {
    private final static String LOG_TAG = "ProductDetailsActivity";
    private static final String TAG_RETAINED_FRAGMENT = "RetainedFragment";

    private RetainedFragment mRetainedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.product_details_activity);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        int targetProductId = extras.getInt("productId");

        FragmentManager fm = getFragmentManager();
        mRetainedFragment = (RetainedFragment) fm.findFragmentByTag(TAG_RETAINED_FRAGMENT);

        if (mRetainedFragment == null) {

            mRetainedFragment = new RetainedFragment();
            fm.beginTransaction().add(mRetainedFragment, TAG_RETAINED_FRAGMENT).commit();

            Product tmpProduct = new Product();
            String productImageThumbUrl = extras.getString("productImageThumbUrl");
            tmpProduct.setImageUrl(productImageThumbUrl);
            bindProduct(tmpProduct);

            getProductById(targetProductId);
        } else {
            bindProduct(mRetainedFragment.getProduct());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    finishAfterTransition();
                } else {
                    Intent intent = NavUtils.getParentActivityIntent(this);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    NavUtils.navigateUpTo(this, intent);
                }

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getProductById(int productId) {
        ApiInterface apiService = RetrofitApiClient.getClient().create(ApiInterface.class);

        Call<ProductResult> call = apiService.getOneProduct(productId, Config.LCBO_API_KEY);
        call.enqueue(new Callback<ProductResult>() {
            @Override
            public void onResponse(Call<ProductResult> call, Response<ProductResult> response) {
                Product product = null;
                if (response.isSuccessful()) {
                    ProductResult productResult = response.body();
                    product = productResult.getResult();
                } else {
                    Log.e(LOG_TAG, "getProductById() - response problem.");
                }
                onGetProductByIdResult(product);
            }
            @Override
            public void onFailure(Call<ProductResult> call, Throwable t) {
                Log.e(LOG_TAG, t.toString());
                onGetProductByIdResult(null);
            }
        });
    }

    private void onGetProductByIdResult(Product product){
        if(product!=null){
            mRetainedFragment.setProduct(product);
            bindProduct(product);
        } else {
            Toast.makeText(this, "Unable to load product!", Toast.LENGTH_LONG).show();
        }
    }

    private void bindProduct(Product product){
        ProductDetailsActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.product_details_activity);
        binding.setProduct(product);
    }

}
