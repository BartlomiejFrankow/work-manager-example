package com.example.network.repository

import com.example.domain.repository.DownloadImageResponse
import com.example.domain.repository.FileRepository
import com.example.network.remote.FileApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(private val api: FileApi) : FileRepository {

    override suspend fun downloadImage(): DownloadImageResponse = withContext(Dispatchers.IO) {
        val result = api.downloadImage()
        return@withContext DownloadImageResponse(result.body()?.bytes(), result.code().toString(), result.isSuccessful)
    }
}
