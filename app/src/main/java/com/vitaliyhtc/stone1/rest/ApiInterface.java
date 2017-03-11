package com.vitaliyhtc.stone1.rest;

import com.vitaliyhtc.stone1.model.ProductResult;
import com.vitaliyhtc.stone1.model.ProductsResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("products")
    Call<ProductsResult> getProductsResult(@Query("page") int page, @Query("per_page") int perPage,
                                           @Query("where_not") String whereNot , @Query("access_key") String accessKey);

    @GET("products/{product_id}")
    Call<ProductResult> getOneProduct(@Path("product_id") int productId, @Query("access_key") String accessKey);

}
