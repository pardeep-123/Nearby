package com.creation.nearby.retrofit

import com.creation.nearby.model.*
import com.creation.nearby.model.auth.LoginModel
import com.creation.nearby.utils.Constants
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface RetrofitInterface {

    @POST(Constants.SIGNUP)
   suspend fun signup(
    @Body map : HashMap<String,String>
    ): Response<LoginModel>

   // login api
   @POST(Constants.LOGIN)
   suspend fun loginApi(
       @Body map : HashMap<String,String>
   ): Response<LoginModel>


    // social login api
    @POST(Constants.socialLogin)
    suspend fun socialLoginApi(
        @Body map : HashMap<String,String>
    ): Response<LoginModel>

    // logout api
    @POST(Constants.logout)
    suspend fun logoutApi(): Response<CommonModel>

    // change password api
    @POST(Constants.changePassword)
    suspend fun changePasswordApi(
        @Body map : HashMap<String,String>
    ): Response<CommonModel>

    // change password api
    @POST(Constants.forgotPassword)
    suspend fun forgetPasswordApi(
        @Body map : HashMap<String,String>
    ): Response<CommonModel>

    @Multipart
   // @FormUrlEncoded
    @POST(Constants.addEvent)
    suspend fun addEventApi(
        @PartMap map : HashMap<String,RequestBody>,
        @Part image : MultipartBody.Part
    ): Response<AddEventModel>

    // to get event listing
    @GET(Constants.eventListing)
    suspend fun eventListingApi(): Response<GetEventModel>

    // to get my event listing
    @GET(Constants.myEventListing)
    suspend fun myEventListingApi(): Response<MyEventModel>

    // to get home listing
    @GET(Constants.homeListing)
    suspend fun homeListing(@QueryMap haQueryMap: HashMap<String,String>) : Response<HomeListingModel>

    @Multipart
    // to upload file
    @POST(Constants.fileUpload)
    suspend fun fileUpload(
        @PartMap map : HashMap<String,RequestBody>,
        @Part image : MultipartBody.Part
    ) : Response<FileUploadModel>

    // Add Feed Api
    @POST(Constants.addFeed)
    suspend fun addFeed(
        @Body map : HashMap<String,String>
    ): Response<CommonModel>

     // user_detail Api
    @FormUrlEncoded
    @POST(Constants.user_detail)
    suspend fun userDetail(
        @Field("profile_id") profile_id : String
    ): Response<UserDetailResponse>

    // to get home listing
    @GET(Constants.getFeed)
    suspend fun feedListing() : Response<GetFeedModel>


    // get swipe user list
    @GET(Constants.swipeList)
    suspend fun swipeUserList(@QueryMap haQueryMap: HashMap<String,String>) : Response<UserListModel>
   }