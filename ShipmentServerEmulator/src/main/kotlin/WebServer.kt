import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.engine.*
object WebServer {
    var isRunning = false
    var shipments = mutableListOf<Shipment>()

    fun runServer() {
        if (!isRunning) {
            embeddedServer(Netty, 8080) {
                routing {
                    get("/update") {
                        call.respondText("Hello, world!", ContentType.Text.Html)
                    }
                    get("/track") {
                        val shipmentId = call.parameters["shipmentId"]
                    }
                }
            }.start(wait = true)
            isRunning = true
        }
    }
}