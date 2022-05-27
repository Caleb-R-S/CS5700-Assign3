package UpdateStrategies

interface ShipmentUpdateStrategy {
    fun update(shipmentId: String, newStatus: String, previousStatus: String, timeStamp: Long, additionalInformation: String = "NONE") : ShipmentUpdate
    // You can get rid of newStatus becuase you will know which strategy youre in
}