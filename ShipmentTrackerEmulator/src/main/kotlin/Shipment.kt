import UpdateStrategies.ShipmentUpdate

abstract class Shipment(initialUpdate: ShipmentUpdate): Subject {
    private val observers = mutableListOf<Observer>()
    val shipmentNotes = mutableListOf<String>()
    val shipmentUpdateHistory = mutableListOf<ShipmentUpdate>()
    val shipmentId = initialUpdate.shipmentId
    var newStatus = initialUpdate.newStatus
    var expectedDeliveryDateTimestamp = initialUpdate.expectedDelivery
    var currentLocation = initialUpdate.location
    init {
        shipmentUpdateHistory.add(initialUpdate)
    }

    override fun addObserver(observer: Observer) {
        observers.add(observer)
    }

    override fun removeObserver(observer: Observer) {
        observers.remove(observer)
    }

    fun update(shipmentUpdate: ShipmentUpdate) {
        shipmentUpdateHistory.add(shipmentUpdate)
        newStatus = shipmentUpdate.newStatus?: newStatus
        expectedDeliveryDateTimestamp = shipmentUpdate.expectedDelivery ?: expectedDeliveryDateTimestamp
        currentLocation = shipmentUpdate.location ?: currentLocation
        if (shipmentUpdate.note != null) {
            shipmentNotes.add(shipmentUpdate.note)
        }
        notifyObservers(shipmentUpdate)
    }

    private fun notifyObservers(shipmentUpdate: ShipmentUpdate) {
        for (observer in observers) {
            observer.update(shipmentUpdate)
        }
    }
}