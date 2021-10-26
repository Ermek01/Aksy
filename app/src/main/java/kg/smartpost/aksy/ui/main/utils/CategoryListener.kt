package kg.smartpost.aksy.ui.main.utils

import kg.smartpost.aksy.data.network.category.model.CategoryModel

interface CategoryListener {
    fun getCategorySuccess(response: List<CategoryModel>)
    fun getCategoryFailure(code: Int?)
}