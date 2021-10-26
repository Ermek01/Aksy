package kg.smartpost.aksy.ui.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kg.smartpost.aksy.data.network.category.repo.CategoryRepository

class CategoryViewModelFactory(
    private val categoryRepository: CategoryRepository,
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CategoryViewModel(categoryRepository) as T
    }

}