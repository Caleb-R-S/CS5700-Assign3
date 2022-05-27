import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun shipmentNotFound(shipmentId: String) {
    Row {
        Row {
            Text("Shipment with id = $shipmentId not found", fontSize = 16.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(24.dp))
        }
    }
}