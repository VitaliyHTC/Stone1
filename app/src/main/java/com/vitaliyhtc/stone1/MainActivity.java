package com.vitaliyhtc.stone1;

import android.app.FragmentManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.vitaliyhtc.stone1.activity.CoreActivity;
import com.vitaliyhtc.stone1.adapter.ProductAdapter;
import com.vitaliyhtc.stone1.data.ProductsDataManager;
import com.vitaliyhtc.stone1.data.RetainedFragment;
import com.vitaliyhtc.stone1.model.Product;

import java.util.List;

public class MainActivity extends CoreActivity {

    private static final String TAG_RETAINED_FRAGMENT = "RetainedFragment";

    private ProductAdapter mProductAdapter;

    private ProductsDataManager mProductsDataManager = new ProductsDataManager(this);

    private RetainedFragment mRetainedFragment;
    private List<Product> mProducts;

    private RecyclerView mRecyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getFragmentManager();
        mRetainedFragment = (RetainedFragment) fm.findFragmentByTag(TAG_RETAINED_FRAGMENT);

        if (mRetainedFragment == null) {

            mRetainedFragment = new RetainedFragment();
            fm.beginTransaction().add(mRetainedFragment, TAG_RETAINED_FRAGMENT).commit();

            initProductsList();
        } else {
            mProducts = mRetainedFragment.getProducts();
            loadProducts(mProducts);
        }

    }



    private void initProductsList(){
        mProductsDataManager.getProductsPage(1);
    }

    public void onProductsListLoaded(List<Product> products, int offset){
        mProducts = products;
        mRetainedFragment.setProducts(products);
        loadProducts(products);
    }

    private void loadProducts(List<Product> initialProductsList){
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mProductAdapter = new ProductAdapter(initialProductsList);
        mRecyclerView.setAdapter(mProductAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        initTouchHelper();
    }



    private void initTouchHelper(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Remove swiped item from list and notify the RecyclerView
                int position = viewHolder.getAdapterPosition();
                mProductAdapter.removeAt(position);
                mProducts.remove(position);
                mRetainedFragment.setProducts(mProducts);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }



    public void onNoInternetConnection(){
        Toast.makeText(this, "Unable to work without internet connection. Please, get internet connection and restart app.",
                Toast.LENGTH_LONG).show();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
