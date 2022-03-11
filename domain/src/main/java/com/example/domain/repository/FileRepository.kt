package com.example.domain.repository

interface FileRepository {

    suspend fun downloadImage(): DownloadImageResponse
}

data class DownloadImageResponse(
    val byteArray: ByteArray?,
    val responseCode: String,
    val isSuccessful: Boolean
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DownloadImageResponse

        if (byteArray != null) {
            if (other.byteArray == null) return false
            if (!byteArray.contentEquals(other.byteArray)) return false
        } else if (other.byteArray != null) return false
        if (responseCode != other.responseCode) return false
        if (isSuccessful != other.isSuccessful) return false

        return true
    }

    override fun hashCode(): Int {
        var result = byteArray?.contentHashCode() ?: 0
        result = 31 * result + responseCode.hashCode()
        result = 31 * result + isSuccessful.hashCode()
        return result
    }
}
