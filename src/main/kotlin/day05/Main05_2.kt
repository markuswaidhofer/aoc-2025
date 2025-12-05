package day05

import utils.readLinesFromResource

fun main() {
    val freshIds = readRanges()
    freshIds.sortBy { it.from }

    var count = 0L
    var i = 0
    while(i < freshIds.size) {
        val current = freshIds[i]
        val currentCount = current.count()
        count += currentCount
        i++
        while(i < freshIds.size && nextRangeOverlaps(current, freshIds[i])) {
            if(freshIds[i].to <= current.to) {
                i++
                continue
            }
            freshIds[i].from = current.to + 1
        }
    }


    println("Answer is $count")
}

fun nextRangeOverlaps(current: Range, next: Range): Boolean {
    return current.to >= next.from
}

private fun readRanges(): MutableList<Range> {
    val input = readLinesFromResource("/inputs/day05/5puzzle.txt")

    val freshIds = mutableSetOf<Range>()
    var evaluateRanges = true
    input.forEach {
        if (it == "") {
            evaluateRanges = false
        } else {
            if (evaluateRanges) {
                freshIds.evaluateAndAddRange(it)
            }
        }
    }
    return freshIds.toMutableList()
}
