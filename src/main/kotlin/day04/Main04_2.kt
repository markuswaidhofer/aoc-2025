package day04

import utils.readLinesFromResource

fun main() {
    var rows: List<String> = readLinesFromResource("/inputs/day04/4puzzle.txt")
    var removed = true

    var accessibleRolls = 0

    while(removed) {
        removed = false
        rows = rows.mapIndexed { x, column ->
            column.mapIndexed { y, it ->
                if(it == ROLL && rows.countRollsAround(x, y) < 4) {
                    accessibleRolls++
                    removed = true
                    '.'
                } else it
            }.joinToString("")
        }
    }
    println("Answer is $accessibleRolls")
}
