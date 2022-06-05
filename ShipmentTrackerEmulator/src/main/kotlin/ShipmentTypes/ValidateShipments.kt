package ShipmentTypes

import Shipment

fun validateShipment(shipment: Shipment, initialTimestamp: Long, expectedDelivery: Long): Boolean {
    val type = if(shipment is BulkShipment) {
        "bulk"
    } else if (shipment is ExpressShipment) {
        "express"
    } else if (shipment is OvernightShipment) {
        "overnight"
    } else {
        "standard"
    }
    val dayInMilliseconds = 86400000
    return if (type == "bulk") {
        expectedDelivery > initialTimestamp + dayInMilliseconds * 3
    } else if (type == "express"){
        expectedDelivery < initialTimestamp + dayInMilliseconds * 3
    } else if (type == "overnight"){
        expectedDelivery < initialTimestamp + dayInMilliseconds
    } else {
        return true
    }
}