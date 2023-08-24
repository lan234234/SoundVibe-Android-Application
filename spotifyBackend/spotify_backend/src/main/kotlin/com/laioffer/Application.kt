package com.laioffer

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
        })
    }
    // TODO: adding the routing configuration here
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        get("/feed") {
            val jsonString = this::class.java.classLoader.getResource("feed.json").readText()
            val json = Json.parseToJsonElement(jsonString)
            call.respondText(json.toString(), ContentType.Application.Json)
        }

        get("/playlists") {
            val jsonString = this::class.java.classLoader.getResource("playlists.json").readText()
            val json = Json.parseToJsonElement(jsonString)
            call.respondText(json.toString(), ContentType.Application.Json)
        }
    }
}