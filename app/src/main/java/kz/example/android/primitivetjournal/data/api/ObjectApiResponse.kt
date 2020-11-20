package kz.example.android.primitivetjournal.data.api

data class ObjectApiResponse<T: Any>(
    val message: String,
    val result: T
)