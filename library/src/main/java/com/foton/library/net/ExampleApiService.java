package com.foton.library.net;

import com.foton.library.net.response.Response;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Retrofit ApiService Example
 *
 * @author wilson
 * @version 1.0
 * @since 2019年9月17日14:45:18
 */
public interface ExampleApiService {

    /**
     * Get请求:
     * http://192.168.0.0/example?city="xxx"
     *
     * @param @Query city
     * @return
     */
    @GET("example")
    Observable<Response<Object>> getWithQuery(@Query("city") String city);

    /**
     * Get请求:
     * http://192.168.0.0/example/北京
     *
     * @param @Path city
     * @return
     */
    @GET("example/{city}")
    Observable<Object> getWithPath(@Path("city") String city);


    /**
     * Get请求:
     * http://192.168.0.0/example/北京?user_id=1&user_name=testName
     *
     * @param @Path和@QueryMap结合
     * @return
     */
    @GET("example/{city}")
    Observable<Object> getWithPathAndQueryMap(@Path("city") String city, @QueryMap Map<String, String> queryParams);

    /**
     * Post请求：
     * http://192.168.0.0/example
     * body参数：{"id":"1","content":"我是评论","user_id":"1001"}
     *
     * @param id      @Field("xxx")
     * @param content @Field("xxx")
     * @param user_id @Field("xxx")
     */
    @FormUrlEncoded
    @POST("example")
    Observable<Response<Object>> post(@Field("id") String id, @Field("content") String content, @Field("user_id") String user_id);


    /**
     * Post请求：
     * http://192.168.0.0/example
     *
     * @param paramsMap @FieldMap
     * @return
     */
    @FormUrlEncoded
    @POST("example")
    Observable<Response<Object>> post(@FieldMap Map<String, String> paramsMap);

    /**
     * Post请求：
     * http://192.168.0.0/example
     *
     * @param reqBean @Body Object
     */
    @POST("example")
    Observable<Response<Object>> post(@Body Object reqBean);

    /**
     * Post请求：
     * http://192.168.0.0/example
     *
     * @param requestList @Body List<Object>
     */
    @POST("example")
    Observable<Response<Object>> post(@Body List<Object> requestList);

    /**
     * 上传文件
     * 文件上传： http://192.168.0.0/upload/
     * 上传文件需要构建RequestBody对象,构建方式example：
     * File file = new File(mFilePath);
     * RequestBody requestBody = new MultipartBody.Builder()
     * .setType(MultipartBody.FORM)
     * .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file))
     * .build();
     *
     * @param requestBody
     * @return
     */
    @POST("upload/")
    Observable<Object> uploadFile(@Body RequestBody requestBody);

    /**
     * Put请求：
     * http://192.168.0.0/example/12345
     *
     * @param comment_id
     */
    @PUT("example/{id}")
    Observable<Object> put(@Path("id") String comment_id);

    /**
     * Put请求：
     * http://192.168.0.0/example/12345?user_id=1001
     *
     * @param comment_id
     * @return
     */
    @PUT("example/{id}")
    Observable<Object> put(@Path("id") String comment_id, @Query("user_id") String user_id);
}
