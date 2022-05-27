package UpdateStrategies

class LostUpdate() : ShipmentUpdateStrategy{
    override fun update(shipmentId: String, previousStatus: String, timeStamp: Long, additionalInformation: String): ShipmentUpdate {
        return ShipmentUpdate(shipmentId = shipmentId, newStatus = "lost", previousStatus = previousStatus, timestamp = timeStamp)
    }

}