/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  java.lang.Object
 *  okhttp3.MultipartBody
 *  okhttp3.MultipartBody$Part
 *  okhttp3.RequestBody
 *  retrofit2.Call
 *  retrofit2.http.Body
 *  retrofit2.http.Headers
 *  retrofit2.http.Multipart
 *  retrofit2.http.POST
 *  retrofit2.http.Part
 */
package com.sccengineer.apihelper;

import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface IRetrofit {

    @POST("api/engineer/login")
    public Call<JsonObject> postRawJSONlogin(@Body JsonObject var1);
    @POST("api/engineer/ping")
    public Call<JsonObject> postRawJSONping(@Body JsonObject var1);

    @Multipart
    @POST("api/FormRequest/Add")
    public Call<JsonObject> uploadImage(@Part MultipartBody.Part multipart,
                                        @Part("sessionId") RequestBody sessionId,
                                        @Part("pressId") RequestBody pressId,
                                        @Part("description") RequestBody description,
                                        @Part("operatorCd") RequestBody operatorCd,
                                        @Part("ver") RequestBody ver);
    @Multipart
    @POST("api/FOCOrder/AddWithInformation")
    public Call<JsonObject> uploadRating2(@Body JsonObject locationPost);
    @Multipart
    @POST("api/FormRequest/ConfirmWithInformation")
    public Call<JsonObject> uploadRating(@Part MultipartBody.Part multipart,
                                         @Part("sessionId") RequestBody sessionId,
                                         @Part("formRequestCd") RequestBody formRequestCd,
                                         @Part("rating") RequestBody rating,
                                         @Part("comments") RequestBody comments,
                                         @Part("isApprove") RequestBody isApprove,
                                         @Part("ver") RequestBody ver,
                                         @Part("monitorCase") RequestBody monitorCase,
                                         @Part("monitorDays") RequestBody monitorDays);

    @Multipart
    @POST("api/FOCOrder/AddWithInformation")
    public Call<JsonObject> uploadfoc(@Part MultipartBody.Part multipart,
                                      @Part("sessionId") RequestBody sessionId,
                                      @Part("pressGuid") RequestBody pressGuid,
                                      @Part("currentImpression") RequestBody currentImpression,
                                      @Part("custNotes") RequestBody custNotes,
                                      @Part("items") RequestBody items,
                                      @Part("ver") RequestBody ver);

    @Multipart
    @POST("api/Chargeable/AddWithInformation")
    public Call<JsonObject> uploadcharge(@Part MultipartBody.Part multipart,
                                         @Part("sessionId") RequestBody sessionId,
                                         @Part("pressGuid") RequestBody pressGuid,
                                         @Part("poNo") RequestBody currentImpression,
                                         @Part("custNotes") RequestBody custNotes,
                                         @Part("items") RequestBody items,
                                         @Part("ver") RequestBody ver);

    @Multipart
    @POST("api/FormRequest/ReopenCaseWithInformation")
    public Call<JsonObject> reopen(@Part MultipartBody.Part multipart,
                                   @Part("sessionId") RequestBody sessionId,
                                   @Part("formRequestCd") RequestBody formRequestCd,
                                   @Part("description") RequestBody description,
                                   @Part("ver") RequestBody ver);
}

