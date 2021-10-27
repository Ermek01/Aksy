package kg.smartpost.aksy.data.network.items.model

data class ModelItems(
    val countItem: Int,
    val items: List<Item>,
    val requestParams: RequestParams
) {
    data class Item(
        val created: String,
        val id: Int,
        val phone: List<String>,
        val photos: List<String>,
        val text: String,
        val video: Int,
        val video_photo: Any?,
        val whatsapp: Int
    )

    data class RequestParams(
        val secret_key: String
    )
}