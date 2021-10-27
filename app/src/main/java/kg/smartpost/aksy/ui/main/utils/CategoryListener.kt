package kg.smartpost.aksy.ui.main.utils

import kg.smartpost.aksy.data.network.category.model.ModelCategory
import kg.smartpost.aksy.data.network.category.model.ModelCategoryItem
import java.util.ArrayList

interface CategoryListener {
    fun getCategorySuccess(response: ModelCategory)
    fun getCategoryFailure(code: Int?)
}