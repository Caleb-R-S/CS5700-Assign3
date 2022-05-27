package UpdateStrategies

class DelayedUpdate() : ShipmentUpdateStrategy{
    override fun update(shipmentId: String, previousStatus: String, timeStamp: Long, additionalInformation: String): ShipmentUpdate {
        return ShipmentUpdate(shipmentId = shipmentId, newStatus = "delayed", previousStatus = previousStatus, timestamp = timeStamp, expectedDelivery = additionalInformation.toLong())
    }

}
