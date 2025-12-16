package day10

fun toMachine(line: String): Machine {
    val indexClosingSquareBracket = line.indexOf("]")
    val indexOpeningCurlyBracket = line.indexOf("{")
    val expectedIndicatorLights = line.substring(1, indexClosingSquareBracket)
    val rawButtons = line.substring(indexClosingSquareBracket + 2, indexOpeningCurlyBracket - 1).split(' ')
    val buttons = rawButtons.map { str -> Button(str.substring(1, str.length - 1).split(',').map { it.toInt() }) }
    val rawJoltage = line.substring(indexOpeningCurlyBracket + 1, line.length - 1)
    val expectedJoltage = rawJoltage.split(',').map { it.toInt() }
    println(expectedJoltage)
    return Machine(expectedIndicatorLights, expectedJoltage, buttons)
}