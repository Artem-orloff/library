package model

data class Author(
    val id: Long,
    val name: String,
    val birth: String,
    val biography: String,
    val books: List<Book>
)
