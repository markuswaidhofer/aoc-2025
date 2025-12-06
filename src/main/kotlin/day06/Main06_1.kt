package day06

import utils.readLinesFromResource
import java.util.regex.Pattern

fun main() {
    val lines = readLinesFromResource("/inputs/day06/6puzzle.txt")
        .map { it.trim().split(Pattern.compile(" +")) }

    val numberOfColumns = lines[0].size
    val numberOfRows = lines.size

    var col = 0
    var total = 0L
    while(col < numberOfColumns) {
        var operator = ""
        val operatorIndex = numberOfRows - 1
        val firstNumberIndex = numberOfRows - 2
        var row = numberOfRows - 1
        var result = 0L
        while(row >= 0) {
            val current = lines[row][col]
            if(row == operatorIndex) {
                operator = current
            } else if(row == firstNumberIndex) {
                result = current.toLong()
            } else {
                if(operator == "+") {
                    result += current.toLong()
                } else {
                    result *= current.toLong()
                }
            }
            row--
        }
        total += result
        col++
    }

    println("Answer is $total")
}