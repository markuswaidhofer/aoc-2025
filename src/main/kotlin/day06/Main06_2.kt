package day06

import utils.readLinesFromResource

fun main() {
    val lines = readLinesFromResource("/inputs/day06/6puzzle.txt")

    val lastLine = lines[lines.size - 1]

    val blocks = mutableListOf<Block>()

    var i = 1
    var cutStart = 0
    while (i < lastLine.length) {
        if (lastLine[i] != ' ') {
            blocks.add(createBlock(lines, cutStart, i - 1))
            cutStart = i
        }
        i++
    }
    blocks.add(createBlock(lines, cutStart))

    val total = blocks.sumOf { it.calculate() }

    println("Answer is $total")
}

fun createBlock(lines: List<String>, cutStart: Int, cutEnd: Int): Block {
    return Block(lines.map { it.substring(cutStart, cutEnd) })
}

fun createBlock(lines: List<String>, cutStart: Int): Block {
    return Block(lines.map { it.substring(cutStart) })
}

data class Block(val lines: List<String>) {
    fun calculate(): Long {
        val operator = lines[lines.size - 1].trim()

        var result = 0L

        var col = lines[0].length - 1
        while (col >= 0) {
            var row = 0
            var number = ""
            while (row < lines.size - 1) {
                number += lines[row][col]
                row++
            }

            val parsed = number.trim().toLong()

            if (col == lines[0].length - 1) {
                result = parsed
            } else {
                if (operator == "*") {
                    result *= parsed
                } else {
                    result += parsed
                }
            }

            col--
        }

        return result
    }
}
