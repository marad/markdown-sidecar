package io.github.marad.obsidianSidecar.server

import io.github.marad.obsidianSidecar.app.Sidecar
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.features.ContentNegotiation
import io.ktor.html.respondHtml
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.request.receiveText
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.applicationEngineEnvironment
import io.ktor.server.engine.connector
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.html.body

class Server(private val sidecar: Sidecar) {
    fun start() {
        val env = applicationEngineEnvironment {
            connector {
                host = "0.0.0.0"
                port = 46054
            }
            module {
                install(CORS) {
                    method(HttpMethod.Options)
                    anyHost()
                    allowNonSimpleContentTypes = true
                }
                install(ContentNegotiation) {
                    jackson {}
                }
                routing {
                    get("/") {
                        call.respondHtml {
                            body {
                                text("Hello World")
                            }
                        }
                    }

                    post("/inbox/urls") {
                        val dto = call.receive<AddUrlToInbox>()
                        sidecar.openInbox().addUrl(dto.url, dto.comment)
                        call.respond(HttpStatusCode.NoContent)
                    }

                    post("/inbox/notes") {
                        sidecar.openInbox().addNote(call.receiveText())
                        call.respond(HttpStatusCode.NoContent)
                    }
                }
            }
        }

        embeddedServer(Netty, env).start(true)
    }

}

data class AddUrlToInbox(val url: String, val comment: String?)

