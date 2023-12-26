import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import model.Author
import model.Book

class Api(private val client: HttpClient) {
    private val baseUrl = "http://127.0.0.1"
    suspend fun addBook(book: Book) = client.post("$baseUrl/book/add"){
        contentType(ContentType.Application.Json)
        setBody(book)
    }

    suspend fun addAuthor(autors: Author) = client.post("$baseUrl/author/add"){
        contentType(ContentType.Application.Json)
        setBody(autors)
    }
}