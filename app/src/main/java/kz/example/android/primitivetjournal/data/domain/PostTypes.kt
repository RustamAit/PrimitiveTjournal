package kz.example.android.primitivetjournal.data.domain

enum class PostTypes {
    GIF{
        override fun toString(): String {
            return "gif"
        }
    },
    MP4{
        override fun toString(): String {
            return "mp4"
        }
    },
    JPG{
        override fun toString(): String {
            return "jpg"
        }
    },
    NOT_SET{
        override fun toString(): String {
            return "not_set"
        }
    }
}

fun String.mapToPostType(): PostTypes {
    return when(this.toLowerCase()){
        PostTypes.GIF.toString() -> PostTypes.GIF
        PostTypes.JPG.toString() -> PostTypes.JPG
        PostTypes.MP4.toString() -> PostTypes.MP4
        else -> PostTypes.NOT_SET
    }
}