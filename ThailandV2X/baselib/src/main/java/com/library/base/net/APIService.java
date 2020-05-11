package com.library.base.net;


import com.library.base.entity.basemodel.ResponseModel;
import com.library.base.entity.bean.TestBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;


public interface APIService {

    /**
     * 测试数据
     *
     * @param params
     * @return
     */
    Call<ResponseModel<List<TestBean>>> getZX(@QueryMap HashMap<String, String> params);
    @GET("/api/news")
    Observable<ResponseModel<List<TestBean>>> getZXh(@QueryMap HashMap<String, String> params);
    Observable<ResponseModel<TestBean>> getTest(@QueryMap HashMap<String, String> params);

    @GET("/api/news")
    Observable<ResponseModel<List<TestBean>>> getZXss(@Query("appkey") String userName, @Query("appsecret") String password);

    @DELETE("/api/news")
    Observable<ResponseModel<List<TestBean>>> getZXhDE(@QueryMap HashMap<String, String> params);

    @PUT("/api/news")
    Observable<ResponseModel<List<TestBean>>> getZXhPT(@QueryMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("/api/auth/authorize")
    Observable<ResponseModel<TestBean>> auth(@FieldMap HashMap<String, String> params);
    /**
     * 上传图片
     */
    @FormUrlEncoded
    @POST("/api/upload/image-batch")
    Observable<ResponseModel<Object>> upImgLoad(@FieldMap HashMap<String, String> params);
// --------------------------测试模型完--------------------

    /**
     * 登录
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("/api/auth/authorize")
    Observable<ResponseModel<TestBean>> login(@FieldMap HashMap<String, String> params);

















    /**
     * 注意1：必须使用{@code @POST}注解为post请求<br>
     * 注意：使用{@code @Multipart}注解方法，必须使用{@code @Part}/<br>
     * {@code @PartMap}注解其参数<br>
     * 本接口中将文本数据和文件数据分为了两个参数，是为了方便将封装<br>
     * {@link MultipartBody.Part}的代码抽取到工具类中<br>
     * 也可以合并成一个{@code @Part}参数
     *
     * @param params 用于封装文本数据
     * @param parts  用于封装文件数据
     * @return BaseResp为服务器返回的基本Json数据的Model类
     */
    @Multipart
    @POST("/api/tool/addImage")
    Call<ResponseModel<TestBean>> requestUploadWork(@PartMap Map<String, RequestBody> params,
                                                   @Part List<MultipartBody.Part> parts);

    /**
     * 注意1：必须使用{@code @POST}注解为post请求<br>
     * 注意2：使用{@code @Body}注解参数，则不能使用{@code @Multipart}注解方法了<br>
     * 直接将所有的{@link MultipartBody.Part}合并到一个{@link MultipartBody}中
     */
    @POST("/api/tool/addImage")
    Call<ResponseModel<TestBean>> requestUploadWork(@Body MultipartBody body);

    @Multipart
    @POST("/api/tool/addImage")
    Call<ResponseModel<TestBean>> uploadFile(@QueryMap HashMap<String, String> params, @Body MultipartBody.Part body);

}
