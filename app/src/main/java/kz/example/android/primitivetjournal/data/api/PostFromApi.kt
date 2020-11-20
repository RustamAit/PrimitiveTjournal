package kz.example.android.primitivetjournal.data.api

data class PostFromApi(
    val id: Int,
    val cover: Cover?,
    val title: String,
    val blocks: List<Block>?
) {
    fun hasExternalSource(): Boolean{
        if(blocks.isNullOrEmpty()){
            return false
        }
        return this.blocks.first().data?.video?.data?.external_service != null
    }
}

data class Cover(
    val additionalData: AdditionalData?,
    val size: Size?,
    val thumbnailUrl: String?,
    val url: String?
)


data class AdditionalData(
    val type: String
)

data class Size(
    val height: Int,
    val width: Int
)

data class Block(
    val `data`: Data?
)

data class Data(
    val video: Video?
)

data class Video(
    val `data`: DataX?
)

data class DataX(
    val external_service: ExternalService?
)

data class ExternalService(
    val id: String,
    val name: String
)
