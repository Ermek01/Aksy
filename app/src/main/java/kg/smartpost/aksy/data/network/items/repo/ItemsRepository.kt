package kg.smartpost.aksy.data.network.items.repo

import kg.smartpost.aksy.data.network.ApiService
import kg.smartpost.aksy.data.network.category.model.ModelSendKey
import kg.smartpost.aksy.utils.BaseRepository


class ItemsRepository(private val apiService: ApiService) : BaseRepository() {

    suspend fun getItems(modelSendKey: String, page: Int, categoryId: Int) = safeApiCall {
        apiService.getItems(modelSendKey, page, categoryId)
    }

    suspend fun getItemById(modelSendKey: String, blogId: Int) = safeApiCall {
        apiService.getItemsById(modelSendKey, blogId)
    }

    suspend fun searchItems(secret_key: String, search: String?, page: Int) = safeApiCall {
        apiService.searchItems(secret_key, search, page)
    }

    suspend fun searchItem(secret_key: String, search: String?) = safeApiCall {
        apiService.searchItem(secret_key, search)
    }

}