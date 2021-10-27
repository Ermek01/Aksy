package kg.smartpost.aksy.ui.items.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kg.smartpost.aksy.data.network.category.repo.CategoryRepository
import kg.smartpost.aksy.data.network.items.repo.ItemsRepository

class ItemsViewModelFactory(
    private val itemsRepository: ItemsRepository,
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ItemsViewModel(itemsRepository) as T
    }

}