package com.creation.nearby.retrofit

import com.creation.nearby.model.CommonModel
import com.creation.nearby.model.auth.LoginModel
import com.creation.nearby.utils.Constants
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

    // logout api
    @POST(Constants.logout)
    suspend fun logoutApi(): Response<CommonModel>

    // change password api
    @POST(Constants.changePassword)
    suspend fun changePasswordApi(
        @Body map : HashMap<String,String>
    ): Response<CommonModel>
   }