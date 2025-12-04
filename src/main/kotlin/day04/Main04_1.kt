package day04

import utils.readLinesFromResource

const val ROLL = '@'

fun main() {
    val rows: List<String> = readLinesFromResource("/inputs/day04/4puzzle.txt")
    var accessibleRolls = 0
    rows.forEachIndexed { x, column ->
        column.forEachIndexed { y, it ->
            if(it == ROLL && rows.countRollsAround(x, y) < 4) {
                accessibleRolls++
            }
        }
    }
    println("Answer is $accessibleRolls")
}

fun List<String>.countRollsAround(x: Int, y: Int): Int {
    val count = isRoll(x - 1, y - 1) + isRoll(x, y - 1) + isRoll(x + 1, y - 1) +
            isRoll(x - 1, y) + isRoll(x + 1, y) +
            isRoll(x - 1, y + 1) + isRoll(x, y + 1) + isRoll(x + 1, y + 1)
    return count
}

fun List<String>.isRoll(x: Int, y: Int): Int = if (this.getSafe(x, y) == ROLL) 1 else 0

fun List<String>.getSafe(x: Int, y: Int): Char {
    return try {
        this[x][y]
    }catch (_: IndexOutOfBoundsException) {
        return '.'
    }
}
