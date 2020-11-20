package kz.example.android.primitivetjournal.data.api

data class ListApiResponse<T: Any>(
    val message: String,
    val result: List<T>
)