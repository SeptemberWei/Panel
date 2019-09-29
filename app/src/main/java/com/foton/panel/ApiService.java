package com.foton.panel;

import com.foton.library.net.response.Response;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * ApiService
 * if u want to know how to use retrofit,please read exaple on com.foton.library.net.ExampleApiService
 *
 * @author wilson
 * @see com.foton.library.net.ExampleApiService
 * @since 2019年9月17日14:56:27
 */
public interface ApiService {

    @FormUrlEncoded
    @POST("/getJoke?")
    Observable<Response<List<Object>>> getPhoneInfo(@FieldMap Map<String, String> map);
}
