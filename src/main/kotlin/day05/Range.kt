package day05

class Range(private val from: Long, private val to: Long) {
    fun contains(toCheck: Long) = toCheck in from..to
}

fun MutableSet<Range>.evaluateAndAddRange(it: String) {
    val parts = it.split("-")
    this.add(Range(parts[0].toLong(), parts[1].toLong()))
}