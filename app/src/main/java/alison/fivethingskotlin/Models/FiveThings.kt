package alison.fivethingskotlin.Models

import alison.fivethingskotlin.Util.getFullDateFormat
import java.util.*

data class FiveThings(
        var date: Date,
        var one: String,
        var two: String,
        var three: String,
        var four: String,
        var five: String,
        var saved: Boolean
) {
    val isComplete: Boolean
        get() {
            return !one.isNullOrEmpty() &&
                    !two.isNullOrEmpty() &&
                    !three.isNullOrEmpty() &&
                    !four.isNullOrEmpty() &&
                    !five.isNullOrEmpty()
        }
    val fullDateString: String
        get() {
            return getFullDateFormat(date)
        }
    val savedString: String
        get() {
            return if (saved) "Saved" else "Save"
        }
}