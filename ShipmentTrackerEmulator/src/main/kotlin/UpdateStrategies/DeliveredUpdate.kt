package UpdateStrategies

class DeliveredUpdate() : ShipmentUpdateStrategy{
    override fun update(shipmentId: String, previousStatus: String, timeStamp: Long, additionalInformation: String): ShipmentUpdate {
        return ShipmentUpdate(shipmentId = shipmentId, newStatus = "delivered", previousStatus = previousStatus, timestamp = timeStamp)
    }

}
