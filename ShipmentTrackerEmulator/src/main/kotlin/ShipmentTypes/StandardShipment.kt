package ShipmentTypes

import Shipment
import UpdateStrategies.ShipmentUpdate

class StandardShipment(initialUpdate: ShipmentUpdate): Shipment(initialUpdate){
    override fun shipmentType(): String {
        return "standard"
    }
}