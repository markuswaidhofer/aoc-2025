package day02

import utils.readLineFromResource

fun main() {
    val sum = readLineFromResource("/inputs/day02/2puzzle.txt")
        .split(",").sumOf {
            val parts = it.split("-")
            Range2(parts[0], parts[1]).countInvalidIds()
        }
    println("Sum is $sum")

}

data class Range2(val start: String, val end: String) {
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
        var patternLengthToCheck = 1
        outer@
        while (patternLengthToCheck <= input.length / 2) {
            if(input.length % patternLengthToCheck != 0) {
                patternLengthToCheck++
                continue
            }

            val pattern = input.take(patternLengthToCheck)
            var checkIndex = pattern.length

            while (checkIndex + pattern.length <= input.length){
                val toCheck = input.substring(checkIndex, checkIndex + pattern.length)
                if(toCheck != pattern) {
                    patternLengthToCheck++
                    continue@outer
                }
                checkIndex+=pattern.length
            }

            return true
        }

        return false
    }
}