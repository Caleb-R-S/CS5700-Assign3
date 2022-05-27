import java.text.SimpleDateFormat
import java.util.*

fun dateConverter(time: Long?): String{
    return if (time != null) {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        format.format(date)
    } else {
        "Unavailable"
    }
}