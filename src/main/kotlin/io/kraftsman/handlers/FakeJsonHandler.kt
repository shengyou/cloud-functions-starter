package io.kraftsman.handlers

import com.github.javafaker.Faker
import com.google.cloud.functions.HttpFunction
import com.google.cloud.functions.HttpRequest
import com.google.cloud.functions.HttpResponse
import io.kraftsman.dtos.News
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException
import java.net.HttpURLConnection
import java.time.LocalDateTime
import java.util.*
import kotlin.jvm.Throws

class FakeJsonHandler : HttpFunction {

    @Throws(IOException::class)
    override fun service(request: HttpRequest, response: HttpResponse) {

        if ("GET" != request.method.toUpperCase()) {
            with(response) {
                setStatusCode(HttpURLConnection.HTTP_BAD_METHOD)
                writer.write("Bad method")
            }

            return
        }

        val limit = request.queryParameters["limit"]?.first()?.toIntOrNull() ?: 10
        val faker = Faker.instance(Locale.TRADITIONAL_CHINESE)

        val news = (1..limit).map { id ->
            News(
                id = id.toString(),
                title = faker.lorem().sentence(),
                author = faker.name().fullName(),
                content = faker.lorem().paragraph(),
                coverUrl = "https://${faker.internet().url()}",
                permalink = "https://${faker.internet().url()}",
                publishedAt = LocalDateTime.now().toString(),
            )
        }

        with(response) {
            setStatusCode(HttpURLConnection.HTTP_OK)
            setContentType("application/json")
            writer.write(
                Json.encodeToString(
                    mapOf("data" to news)
                )
            )
        }
    }
}
