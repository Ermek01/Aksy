package kg.smartpost.aksy.ui.items.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.smartpost.aksy.data.network.NetworkResponse
import kg.smartpost.aksy.data.network.category.model.ModelSendKey
import kg.smartpost.aksy.data.network.category.repo.CategoryRepository
import kg.smartpost.aksy.data.network.items.repo.ItemsRepository
import kg.smartpost.aksy.ui.items.utils.ItemsByIdListener
import kg.smartpost.aksy.ui.items.utils.ItemsListener
import kg.smartpost.aksy.ui.main.utils.CategoryListener
import kotlinx.coroutines.launch

class ItemsViewModel(
    private val itemsRepository: ItemsRepository,
) : ViewModel() {

    private var itemsListener: ItemsListener? = null
    private var itemsByIdListener: ItemsByIdListener? = null

    fun setItemsListener(itemsListener: ItemsListener) {
        this.itemsListener = itemsListener
    }

    fun setItemsByIdListener(itemsByIdListener: ItemsByIdListener) {
        this.itemsByIdListener = itemsByIdListener
    }

    fun getItems(modelSendKey: String, page: Int) = viewModelScope.launch {
        when (val response = itemsRepository.getItems(modelSendKey, page)) {
            is NetworkResponse.Success -> {
                itemsListener?.getItemsSuccess(response.value)
            }
            is NetworkResponse.Failure -> {
                itemsListener?.getItemsFailure(response.errorCode)
            }
        }
    }

    fun getItemsById(modelSendKey: String, blogId: Int) = viewModelScope.launch {
        when (val response = itemsRepository.getItemById(modelSendKey, blogId)) {
            is NetworkResponse.Success -> {
                itemsByIdListener?.getItemsSuccess(response.value)
            }
            is NetworkResponse.Failure -> {
                itemsByIdListener?.getItemsFailure(response.errorCode)
            }
        }
    }
}