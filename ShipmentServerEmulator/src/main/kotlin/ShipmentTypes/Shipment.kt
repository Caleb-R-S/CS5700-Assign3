package ShipmentTypes

import UpdateStrategies.ShipmentUpdate

abstract class Shipment(initialUpdate: ShipmentUpdate) {
    val shipmentNotes = mutableListOf<String>()
    val shipmentUpdateHistory = mutableListOf<ShipmentUpdate>()
    val shipmentId = initialUpdate.shipmentId
    var newStatus = initialUpdate.newStatus
    var expectedDeliveryDateTimestamp = initialUpdate.expectedDelivery
    var currentLocation = initialUpdate.location
    init {
        shipmentUpdateHistory.add(initialUpdate)
    }


    fun update(shipmentUpdate: ShipmentUpdate) {
        shipmentUpdateHistory.add(shipmentUpdate)
        newStatus = shipmentUpdate.newStatus?: newStatus
        expectedDeliveryDateTimestamp = shipmentUpdate.expectedDelivery ?: expectedDeliveryDateTimestamp
        currentLocation = shipmentUpdate.location ?: currentLocation
        if (shipmentUpdate.note != null) {
            shipmentNotes.add(shipmentUpdate.note)
        }
    }
}