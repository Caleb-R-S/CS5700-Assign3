package UpdateStrategies

interface ShipmentUpdateStrategy {
    fun update(shipmentId: String, previousStatus: String, timeStamp: Long, additionalInformation: String = "NONE") : ShipmentUpdate
}