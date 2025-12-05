package day05

import utils.readLinesFromResource

fun main() {
    val input = readLinesFromResource("/inputs/day05/5puzzle.txt")

    val freshIds = mutableSetOf<Range>()
    val availableIds = mutableSetOf<Long>()
    var evaluateRanges = true
    input.forEach {
        if(it == "") {
            evaluateRanges = false
        } else {
            if(evaluateRanges) {
                freshIds.evaluateAndAddRange(it)
            } else {
                availableIds.add(it.toLong())
            }
        }
    }

    var freshAvailableIds = 0

    availableIds.forEach { id ->
        if(freshIds.any { it.contains(id) }) {
            freshAvailableIds ++
        }
    }

    println("Answer is $freshAvailableIds")
}
