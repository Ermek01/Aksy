package kg.smartpost.aksy.data.network.items.model

data class ModelItemDetail(
    val item: Item,
    val requestParams: RequestParams
) {
    data class Item(
        val created: String,
        val id: Int,
        val phone: List<String>,
        val photos: List<String>,
        val text: String,
        val video: Int,
        val video_photo: String,
        val whatsapp: Int
    )

    data class RequestParams(
        val blogId: String,
        val secret_key: String
    )
}