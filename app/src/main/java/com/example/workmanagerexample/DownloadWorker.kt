package com.example.workmanagerexample

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.domain.common.Constants.ERROR_MSG
import com.example.domain.common.Constants.IMAGE_NAME
import com.example.domain.common.Constants.IMAGE_URI
import com.example.domain.common.Constants.NOTIFICATION_CHANNEL
import com.example.domain.repository.DownloadImageResponse
import com.example.domain.useCase.DownloadImageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.random.Random

class DownloadWorker(
    private val context: Context,
    private val workerParams: WorkerParameters,
    private val downloadImageUseCase: DownloadImageUseCase
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        startForegroundService()
        delay(5000L)

        val response = downloadImageUseCase()

        writeStream(response)

        if (!response.isSuccessful) {
            if (response.responseCode.startsWith("5")) return Result.retry()

            return Result.failure(workDataOf(ERROR_MSG to "Network error"))
        }

        return Result.failure(workDataOf(ERROR_MSG to "Unknown error"))
    }

    private suspend fun writeStream(response: DownloadImageResponse) {
        response.byteArray?.let { bytes ->
            withContext(Dispatchers.IO) {
                val file = File(context.cacheDir, IMAGE_NAME)

                FileOutputStream(file).use { stream ->
                    try {
                        stream.write(bytes)
                    } catch (e: IOException) {
                        return@withContext Result.failure(workDataOf(ERROR_MSG to e.localizedMessage))
                    }
                }
                Result.success(workDataOf(IMAGE_URI to file.toUri().toString()))
            }
        }
    }

    private suspend fun startForegroundService() {
        setForeground(
            ForegroundInfo(
                Random.nextInt(),
                NotificationCompat.Builder(context, NOTIFICATION_CHANNEL)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(context.getString(R.string.downloading))
                    .setContentTitle(context.getString(R.string.download_in_progress))
                    .build()
            )
        )
    }
}
