package UpdateStrategies

class ShippedUpdate() : ShipmentUpdateStrategy{
    override fun update(shipmentId: String, newStatus: String, previousStatus: String, timeStamp: Long, additionalInformation: String): ShipmentUpdate {
        return ShipmentUpdate(shipmentId = shipmentId, newStatus = newStatus, previousStatus = previousStatus, timestamp = timeStamp, expectedDelivery = additionalInformation.toLong())
    }
}