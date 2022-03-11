package com.example.domain.useCase

import com.example.domain.repository.FileRepository
import javax.inject.Inject

class DownloadImageUseCase @Inject constructor(private val repository: FileRepository) {

    suspend operator fun invoke() = repository.downloadImage()
}
