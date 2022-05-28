import ShipmentTypes.*
import UpdateStrategies.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.cors.routing.*

object WebServerShipment {
    var isRunning = false
    var shipments = mutableListOf<Shipment>()
    private val shippingStrategies = mapOf<String, ShipmentUpdateStrategy>(
        Pair("created", CreatedUpdate()),
        Pair("canceled", CanceledUpdate()),
        Pair("delayed", DelayedUpdate()),
        Pair("delivered", DeliveredUpdate()),
        Pair("location", LocationUpdate()),
        Pair("lost", LostUpdate()),
        Pair("noteadded", NoteAddedUpdate()),
        Pair("shipped", ShippedUpdate()),
    )
    fun runServer() {
        if (!isRunning) {
            embeddedServer(Netty, 8080) {
                install(CORS) {
//                    allowSameOrigin = true
//                    allowHost("127.0.0.1:8080")
//                    allowHeader(HttpHeaders.ContentType)
                    // This works but may be a little too open
                    anyHost()
                }
                routing {
                    get("/update") {
                        val shipmentUpdate = shippingStrategies[call.parameters["newStatus"]]?.update(
                            shipmentId = call.parameters["shipmentId"] ?: "Unavailable",
                            previousStatus = shipments.find { it.shipmentId == call.parameters["shipmentId"] }?.newStatus ?: "Unavailable",
                            timeStamp = call.parameters["timeStamp"]?.toLong() ?: 0,
                            additionalInformation = call.parameters["additionalInformation"] ?: "None"
                        )
                        println(call.parameters)
                        val isFound = shipments.find { it.shipmentId == (shipmentUpdate?.shipmentId ?: "NONE") }
//                        if (isFound)

                        call.response.headers
                        call.respondText("success", ContentType.Text.Html)
                    }

                    get("/track") {
                        val shipmentId = call.parameters["shipmentId"]
                    }
                }
            }.start(wait = true)
            isRunning = true
        }
    }

    fun shipmentFactory(initialUpdate: ShipmentUpdate, shipmentType: String): Shipment {
        var shipment: Shipment
        if (shipmentType == "Bulk") {
            shipment = BulkShipment(initialUpdate)
        } else if (shipmentType == "Express") {
            shipment = ExpressShipment(initialUpdate)
        } else if (shipmentType == "Overnight") {
            shipment = OvernightShipment(initialUpdate)
        } else if (shipmentType == "Standard") {
            shipment = StandardShipment(initialUpdate)
        } else {
            throw RuntimeException("Invalid Shipment Type")
        }
        return shipment
    }

}