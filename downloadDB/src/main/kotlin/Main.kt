import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import model.Author
import model.Book
import java.io.File
import java.io.FileReader

suspend fun main(){
    val client = HttpClient(CIO){
        install(ContentNegotiation){
            json()
        }
    }
    val api = Api(client)
    val autorsJson = FileReader(File("D:\\DB\\Authors.json")).readText()
    val autors = Json.decodeFromString<List<Author>>(autorsJson)

    val bookJson = FileReader(File("D:\\DB\\Book.json")).readText()
    val book = Json.decodeFromString<List<Book>>(bookJson)

    book.forEach { api.addBook(it) }
    autors.forEach { api.addAuthor(it) }
}
