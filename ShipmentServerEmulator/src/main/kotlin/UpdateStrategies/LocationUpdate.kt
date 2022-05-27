package UpdateStrategies

class LocationUpdate() : ShipmentUpdateStrategy{
    override fun update(shipmentId: String, previousStatus: String, timeStamp: Long, additionalInformation: String): ShipmentUpdate {
        return ShipmentUpdate(shipmentId = shipmentId, newStatus = "location", previousStatus = previousStatus, timestamp = timeStamp, location = additionalInformation)
    }

}
