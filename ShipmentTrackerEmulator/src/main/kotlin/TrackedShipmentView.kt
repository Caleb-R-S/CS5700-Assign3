import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun trackShipmentView(viewHelper: ShipmentViewHelper) {
        Row {
            Column(modifier = Modifier.padding(8.dp)) {
                Row {
                    Text("Tracking Shipment: ${viewHelper.shipmentId}", fontSize = 32.sp)
                }
                Row {
                    Text(
                        "Status: ${viewHelper.shipmentStatus}"
                                + "\n"
                                + "Location: ${viewHelper.location?:"Unavailable"}"
                                + "\n"
                                + "Expected Delivery: ${dateConverter(viewHelper.expectedDeliveryDate)}"
                    )
                }
                Row {
                    Text("The shipment ${if (viewHelper.isOnTrack) {"is"} else {"is NOT"}} on track")
                }
                Row {
                    Text("Status Updates:")
                }
                Row {
                    Column {
                        for (shipmentUpdate in viewHelper.shipmentUpdateHistory) {
                            Text("Shipment went from ${shipmentUpdate.previousStatus} to ${shipmentUpdate.newStatus} at ${dateConverter(shipmentUpdate.timestamp)}")
                        }
                    }
                }
                Row {
                    Text("Notes:")
                }
                Row {
                    Column {
                        for (note in viewHelper.shipmentNotes) {
                            Text("$note")
                        }
                    }
                }
            }
        }
}