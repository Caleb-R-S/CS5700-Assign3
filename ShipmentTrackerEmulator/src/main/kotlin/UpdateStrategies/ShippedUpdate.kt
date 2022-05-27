package UpdateStrategies

class ShippedUpdate() : ShipmentUpdateStrategy{
    override fun update(shipmentId: String, previousStatus: String, timeStamp: Long, additionalInformation: String): ShipmentUpdate {
        return ShipmentUpdate(shipmentId = shipmentId, newStatus="shipped", previousStatus = previousStatus, timestamp = timeStamp, expectedDelivery = additionalInformation.toLong())
    }
}