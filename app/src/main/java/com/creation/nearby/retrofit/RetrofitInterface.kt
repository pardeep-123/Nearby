package com.creation.nearby.retrofit

import com.creation.nearby.model.auth.LoginModel
import com.creation.nearby.utils.Constants
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RetrofitInterface {

    @FormUrlEncoded
    @POST(Constants.SIGNUP)
   suspend fun signup(
    @FieldMap map : HashMap<String,RequestBody>
    ): Response<LoginModel>
   }