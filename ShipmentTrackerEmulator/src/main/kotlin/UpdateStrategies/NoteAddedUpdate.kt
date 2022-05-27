package UpdateStrategies

class NoteAddedUpdate() : ShipmentUpdateStrategy{
    override fun update(shipmentId: String, previousStatus: String, timeStamp: Long, additionalInformation: String): ShipmentUpdate {
        return ShipmentUpdate(shipmentId = shipmentId, newStatus = "noteadded", previousStatus = previousStatus, timestamp = timeStamp, note = additionalInformation)
    }

}