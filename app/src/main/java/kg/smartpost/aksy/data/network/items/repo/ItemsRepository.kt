package kg.smartpost.aksy.data.network.items.repo

import kg.smartpost.aksy.data.network.ApiService
import kg.smartpost.aksy.data.network.category.model.ModelSendKey
import kg.smartpost.aksy.utils.BaseRepository


class ItemsRepository(private val apiService: ApiService) : BaseRepository() {

    suspend fun getItems(modelSendKey: String) = safeApiCall {
        apiService.getItems(modelSendKey)
    }

}