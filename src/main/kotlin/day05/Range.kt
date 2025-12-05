package day05

data class Range(var from: Long, val to: Long) {
    fun contains(toCheck: Long) = toCheck in from..to
    fun count(): Long = to - from + 1
}

fun MutableSet<Range>.evaluateAndAddRange(it: String) {
    val parts = it.split("-")
    this.add(Range(parts[0].toLong(), parts[1].toLong()))
}