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
    private var isRunning = false
    private var shipments = mutableListOf<Shipment>()
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
    // suspend
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
                        var message = "Failed: Could not update shipment"
                        val shipmentUpdate = shippingStrategies[call.parameters["newStatus"]]?.update(
                            shipmentId = call.parameters["shipmentId"] ?: "Unavailable",
                            previousStatus = shipments.find { it.shipmentId == call.parameters["shipmentId"] }?.newStatus ?: "Unavailable",
                            timeStamp = call.parameters["timestamp"]?.toLong() ?: 0,
                            additionalInformation = call.parameters["additionalInformation"] ?: "None"
                        )
                        println(call.parameters)
                        var shipmentToUpdate: Shipment? = findShipment(shipmentUpdate?.shipmentId.toString())
                        if (shipmentToUpdate != null) {
                            shipmentToUpdate.update(shipmentUpdate!!)
                            message = "Success: Updated to ${call.parameters["newStatus"]}"
                            if (shipmentUpdate.newStatus in listOf("lost", "canceled")) {
                                message += "\n Please note that the shipment will not be delivered"
                            } else if (shipmentUpdate.newStatus in listOf("shipped", "delayed")) {
                                val stillOnTime = validateShipment(shipmentToUpdate, shipmentToUpdate.shipmentCreatedTimestamp!!, shipmentUpdate.expectedDelivery!!)
                                if (!stillOnTime) {
                                    message += "\n Please note that the shipment may not arrive on time"
                                }
                            }
                        } else {
                            if (call.parameters["newStatus"] == "created") {
                                shipmentToUpdate = shipmentFactory(initialUpdate = shipmentUpdate!!, shipmentType = call.parameters["type"].toString())
                                shipments.add(shipmentToUpdate)
                                message = "Success: Shipment created"
                            } else {
                                message = "Failed to create shipment ${call.parameters["shipmentId"]}. Please set status to \"created\""
                            }
                        }

                        call.respondText(message, ContentType.Text.Html)
                    }

                }
            }.start(wait = false) // was true
            isRunning = true
        }
    }

    fun shipmentFactory(initialUpdate: ShipmentUpdate, shipmentType: String): Shipment {
        var shipment: Shipment
        if (shipmentType == "bulk") {
            shipment = BulkShipment(initialUpdate)
        } else if (shipmentType == "express") {
            shipment = ExpressShipment(initialUpdate)
        } else if (shipmentType == "overnight") {
            shipment = OvernightShipment(initialUpdate)
        } else if (shipmentType == "standard") {
            shipment = StandardShipment(initialUpdate)
        } else {
            shipment = InvalidShipment(initialUpdate)
        }
        return shipment
    }

    fun findShipment(shipmentId: String): Shipment?{
        return shipments.find { it.shipmentId == (shipmentId) }
    }

}