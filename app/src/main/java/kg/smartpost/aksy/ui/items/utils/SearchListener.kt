package kg.smartpost.aksy.ui.items.utils

import kg.smartpost.aksy.data.network.items.model.ModelItems
import kg.smartpost.aksy.data.network.items.model.ModelSearchItems

interface SearchListener {
    fun searchItemsSuccess(modelItems: ModelSearchItems)
    fun searchItemsFailure(code: Int?)
}