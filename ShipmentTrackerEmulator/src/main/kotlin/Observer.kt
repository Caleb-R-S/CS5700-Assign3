import UpdateStrategies.ShipmentUpdate

interface Observer {
    fun update(shippingUpdate: ShipmentUpdate)
}