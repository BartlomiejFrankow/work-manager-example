package com.example.network.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface FileApi {

    @GET("/packs/media/components/global/search-explore-nav/img/vectors/term-bg-1-666de2d941529c25aa511dc18d727160.jpg")
    suspend fun downloadImage(): Response<ResponseBody>

}
