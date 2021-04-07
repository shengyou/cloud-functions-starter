package io.kraftsman.handlers

import com.google.cloud.functions.HttpFunction
import com.google.cloud.functions.HttpRequest
import com.google.cloud.functions.HttpResponse
import java.io.IOException
import java.net.HttpURLConnection
import kotlin.jvm.Throws

class PlainTextHandler: HttpFunction {

    @Throws(IOException::class)
    override fun service(request: HttpRequest, response: HttpResponse) {

        with(response) {
            setStatusCode(HttpURLConnection.HTTP_OK)
            setContentType("text/plain")
            writer.write("Hello, Kotlin Serverless")
        }
    }
}
