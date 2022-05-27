import UpdateStrategies.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

class TrackingSimulator(val coroutineScope: CoroutineScope) {
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
    val shipments = mutableListOf<Shipment>()
    var waitTime = 1000

    fun runSimulation() {
        coroutineScope.launch {
            readData()
        }
    }

    private fun addShipment(shipment: Shipment) {
        shipments.add(shipment)
    }

    fun findShipment(id: String) : Shipment?{
        for (shipment in shipments) {
            if (shipment.shipmentId == id) {
                return shipment
            }
        }
        return null
    }

    private suspend fun readData() {
        val updates = File(System.getProperty("user.dir") + "\\src\\main\\resources\\test.txt").useLines { it.toList() }
        var shipmentHistory = mutableMapOf<String, MutableList<String>>()

        for (update in updates) {

            val updateDetails = update.split(",")
            var shipmentFound = false
            var shipmentToUpdate: Shipment? = null
            // Use the lists find method
            for (shipment in shipments) {
                if (updateDetails[1] == shipment.shipmentId) {
                    shipmentToUpdate = shipment
                    shipmentFound = true
                    break
                }
            }
            var additionalInformation = "NONE"
            if (updateDetails.size > 3) {
                additionalInformation = updateDetails[3]
            }

            var previousStatus: String?
            if (updateDetails[1] in shipmentHistory) {
                previousStatus = shipmentHistory[updateDetails[1]]?.last()
                shipmentHistory[updateDetails[1]]?.add(updateDetails[0])
            } else {
                previousStatus = "order processing"
                shipmentHistory[updateDetails[1]] = mutableListOf<String>()
                shipmentHistory[updateDetails[1]]?.add(updateDetails[0])
            }

            val shipmentUpdate = shippingStrategies[updateDetails[0]]?.update(
                shipmentId = updateDetails[1],
                previousStatus = previousStatus.toString(),
                timeStamp = updateDetails[2].toLong(),
                additionalInformation = additionalInformation
            )
            if (shipmentFound && shipmentToUpdate != null) {
                if (shipmentUpdate != null) {
                    shipmentToUpdate.update(shipmentUpdate)
                }
            } else {
                if (shipmentToUpdate == null) {
                    if (shipmentUpdate != null) {
                        shipmentToUpdate = Shipment(shipmentUpdate)
                        addShipment(shipmentToUpdate)
                    }
                }
            }
            delay(waitTime.toLong())
        }
    }

}