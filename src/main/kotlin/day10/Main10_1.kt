package day10

import utils.readLinesFromResource

fun main() {
    val lines = readLinesFromResource("/inputs/day10/10puzzle.txt")
    val machines: List<Machine> = lines.map { toMachine(it) }
    val minSwitches = machines.map { it.calculateMinSwitches() }
    val sum = minSwitches.sum()
    println("Answer is $sum")
}

fun toMachine(line: String): Machine {
    val indexClosingSquareBracket = line.indexOf("]")
    val indexOpeningCurlyBracket = line.indexOf("{")
    val expectedIndicatorLights = line.substring(1, indexClosingSquareBracket)
    val rawButtons = line.substring(indexClosingSquareBracket + 2, indexOpeningCurlyBracket - 1).split(' ')
    val buttons = rawButtons.map { str -> Button(str.substring(1, str.length - 1).split(',').map { it.toInt() }) }
    return Machine(expectedIndicatorLights, buttons)
}
