package kg.smartpost.aksy.ui.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.smartpost.aksy.data.network.NetworkResponse
import kg.smartpost.aksy.data.network.category.model.ModelSendKey
import kg.smartpost.aksy.data.network.category.repo.CategoryRepository
import kg.smartpost.aksy.ui.main.utils.CategoryListener
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val categoryRepository: CategoryRepository,
) : ViewModel() {

    private var categoryListener: CategoryListener? = null

    fun setFloorListener(categoryListener: CategoryListener) {
        this.categoryListener = categoryListener
    }

    fun getFloor(modelSendKey: ModelSendKey) = viewModelScope.launch {

        when (val response =
            categoryRepository.getCategories(modelSendKey)) {
            is NetworkResponse.Success -> {
                categoryListener?.getCategorySuccess(response.value)
            }
            is NetworkResponse.Failure -> {
                categoryListener?.getCategoryFailure(response.errorCode)
            }
        }
    }
}