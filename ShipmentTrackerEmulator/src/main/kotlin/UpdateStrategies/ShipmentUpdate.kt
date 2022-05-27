package UpdateStrategies

data class ShipmentUpdate(val shipmentId: String? = null, val newStatus: String? = null, val previousStatus: String?, val timestamp: Long? = null, val expectedDelivery: Long? = null, val note: String? = null, val location: String? = null)