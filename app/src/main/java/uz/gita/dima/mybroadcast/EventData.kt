package uz.gita.dima.mybroadcast

data class EventData(
    val id: Int = 0,
    val text: String,
    val action: String,
    val imageChecked: Int,
    val imageNotChecked: Int,
    var isChecked: Boolean = false
)
