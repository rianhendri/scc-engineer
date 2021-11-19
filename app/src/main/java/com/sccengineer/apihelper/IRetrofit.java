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
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface IRetrofit {

    @POST("api/engineer/login")
    public Call<JsonObject> postRawJSONlogin(@Body JsonObject var1);
    @POST("api/Notification/ReadAll")
    public Call<JsonObject> ReadAll(@Body JsonObject var1);
    @POST("api/serviceTicket/list")
    public Call<JsonObject> ListST(@Body JsonObject var1);
    @POST("api/approval/list")
    public Call<JsonObject> ListApproval(@Body JsonObject var1);
    @POST("api/engineer/GetCurrentLiveChatList")
    public Call<JsonObject> livechastlist(@Body JsonObject var1);
    @POST("api/engineer/GetCurrentSupportLiveChatList")
    public Call<JsonObject> livecslist(@Body JsonObject var1);
    @GET("chat.json")
    public Call<JsonObject> getchat(@Body JsonObject var1);
    @POST("api/serviceticket/FlagLiveChat")
    public Call<JsonObject> ping(@Body JsonObject var1);
    @POST("api/attendance/ReClockInActiveList")
    public Call<JsonObject> ListReclock(@Body JsonObject var1);
    @POST("api/Notification/Get")
    public Call<JsonObject> postRawJSONgetnotifget(@Body JsonObject var1);
    @POST("api/Notification/Read")
    public Call<JsonObject> Read(@Body JsonObject var1);
    @POST("api/Notification/List")
    public Call<JsonObject> notifications(@Body JsonObject var1);
    @POST("api/engineer/logout")
    public Call<JsonObject> postRawJSONlogout(@Body JsonObject var1);
    @POST("api/serviceTicket/get")
    public Call<JsonObject> getservice(@Body JsonObject var1);
    @POST("api/serviceTicket/getsparepartlist")
    public Call<JsonObject> getpart(@Body JsonObject var1);
    @POST("api/engineer/ChangeLanguage")
    public Call<JsonObject> changelanguage(@Body JsonObject var1);
    @POST("api/engineer/changepassword")
    public Call<JsonObject> changepassword(@Body JsonObject var1);

    @POST("api/engineer/ping")
    public Call<JsonObject> postRawJSONping(@Body JsonObject var1);
    @POST("api/engineer/config")
    public Call<JsonObject> postRawJSONconfig(@Body JsonObject var1);
    @POST("api/engineer/GetLiveChat")
    public Call<JsonObject> getlivechat(@Body JsonObject var1);
    @POST("api/engineer/GetSupportLiveChat")
    public Call<JsonObject> getlivechatcs(@Body JsonObject var1);
    @POST("api/attendance/prepareClockIn")
    public Call<JsonObject> prefclock(@Body JsonObject var1);
    @POST("api/attendance/clockOut")
    public Call<JsonObject> clockout(@Body JsonObject var1);

    @POST("api/approval/response")
    public Call<JsonObject> responsea(@Body JsonObject var1);
    @POST("api/attendance/ApprovalClockIn")
    public Call<JsonObject> approvreclock(@Body JsonObject var1);

    @POST("api/attendance/changeRoleRequest")
    public Call<JsonObject> changerole(@Body JsonObject var1);
    @POST("api/attendance/clockIn")
    public Call<JsonObject> clockin(@Body JsonObject var1);
    @POST("api/attendance/list")
    public Call<JsonObject> attendancelist(@Body JsonObject var1);
    @POST("api/serviceticket/startProgress")
    public Call<JsonObject> startprog(@Body JsonObject var1);
    @POST("api/serviceticket/ScheduledProgress")
    public Call<JsonObject> startprogsched(@Body JsonObject var1);
    @POST("api/serviceticket/cancel")
    public Call<JsonObject> canclerequest(@Body JsonObject var1);
    @POST("api/serviceticket/update")
    public Call<JsonObject> updatea(@Body JsonObject var1);
    @POST("api/serviceticket/UpdateEstimationDate")
    public Call<JsonObject> UpdateEsti(@Body JsonObject var1);
    @POST("api/serviceTicket/GetMessageTemplateList")
    public Call<JsonObject> generatelist(@Body JsonObject var1);
    @GET("chat.json?auth=EGEEh1XOQyHdrTP8UZvedE79LBCH0mkmamZZOs0m")
    public Call<JsonObject> getjsonchat();
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

