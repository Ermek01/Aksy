package kg.smartpost.aksy.ui.items.utils

import kg.smartpost.aksy.data.network.items.model.ModelItems

interface ItemsListener {
    fun getItemsSuccess(modelItems: ModelItems)
    fun getItemsFailure(code: Int?)
}