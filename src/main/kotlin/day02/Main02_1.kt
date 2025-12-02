package day02

import utils.readLineFromResource

fun main() {
    val sum = readLineFromResource("/inputs/day02/2puzzle.txt")
        .split(",").sumOf {
            val parts = it.split("-")
            Range(parts[0], parts[1]).countInvalidIds()
        }
    println("Sum is $sum")

}

data class Range(val start: String, val end: String) {
    fun countInvalidIds(): Long {
        val startNumber = start.toLong()
        val endNumber = end.toLong()
        var current = startNumber
        var count = 0L
        while (current <= endNumber) {
            if(doesRepeat(current.toString())) {
                println("found $current")
                count+=current
            }
            current++
        }
        return count
    }

    private fun doesRepeat(input: String): Boolean {
        if(input.length % 2 != 0) {
            return false
        }
        val patternLengthToCheck = input.length / 2

        val pattern = input.take(patternLengthToCheck)
        val toCheck = input.substring(patternLengthToCheck)

        return pattern == toCheck

    }
}