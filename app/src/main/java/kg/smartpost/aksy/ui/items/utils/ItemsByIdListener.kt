package kg.smartpost.aksy.ui.items.utils

import kg.smartpost.aksy.data.network.items.model.ModelItemDetail
import kg.smartpost.aksy.data.network.items.model.ModelItems

interface ItemsByIdListener {
    fun getItemsSuccess(response: ModelItemDetail)
    fun getItemsFailure(code: Int?)
}