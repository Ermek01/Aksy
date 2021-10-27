package kg.smartpost.aksy.data.network.category.repo

import kg.smartpost.aksy.data.network.ApiService
import kg.smartpost.aksy.data.network.category.model.ModelSendKey
import kg.smartpost.aksy.utils.BaseRepository


class CategoryRepository(private val apiService: ApiService) : BaseRepository() {

    suspend fun getCategories(modelSendKey: String) = safeApiCall {
        apiService.getCategories(modelSendKey)
    }

}