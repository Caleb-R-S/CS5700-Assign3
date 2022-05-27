package UpdateStrategies

class CreatedUpdate() : ShipmentUpdateStrategy{
    override fun update(shipmentId: String, previousStatus: String, timeStamp: Long, additionalInformation: String): ShipmentUpdate {
        return ShipmentUpdate(shipmentId = shipmentId, newStatus = "canceled", previousStatus = previousStatus, timestamp = timeStamp)
    }

}