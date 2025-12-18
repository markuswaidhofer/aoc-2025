package day10

import kotlin.math.pow

private const val ON = '#'
private const val OFF = '.'

class Machine(val expectedIndicatorLights: String, val expectedJoltageRequirements: List<Int>, val buttons: List<Button>) {

    fun calculateMinSwitches(): Int {
        var min = Int.MAX_VALUE
        val possibleCombinations = 2.0.pow(buttons.size).toInt()

        var combination = 0
        while (combination < possibleCombinations) {
            val actualIndicatorLights: MutableList<Char> = turnedOffIndicatorLights()

            var buttonPresses = 0
            val useButton = List(buttons.size) { (combination shr it) and 1 == 1 }
            buttons.forEachIndexed { buttonIndex, button ->
                if (useButton[buttonIndex]) {
                    pushButton(button, actualIndicatorLights)
                    buttonPresses++
                }
            }

            if (actualEqualsExpected(expectedIndicatorLights, actualIndicatorLights) && buttonPresses < min) {
                min = buttonPresses
            }

            combination++
        }

        return min
    }

    fun findCombinations(expectedIndicatorLights: String): List<List<Button>> {
        val combinations: MutableList<List<Button>> = mutableListOf()
        val possibleCombinations = 2.0.pow(buttons.size).toInt()

        var combination = 0
        while (combination < possibleCombinations) {
            val actualIndicatorLights: MutableList<Char> = turnedOffIndicatorLights()
            val pressedButtons = mutableListOf<Button>()

            val useButton = List(buttons.size) { (combination shr it) and 1 == 1 }
            buttons.forEachIndexed { buttonIndex, button ->
                if (useButton[buttonIndex]) {
                    pushButton(button, actualIndicatorLights)
                    pressedButtons.add(button)
                }
            }

            if (actualEqualsExpected(expectedIndicatorLights, actualIndicatorLights)) {
                combinations.add(pressedButtons)
            }

            combination++
        }

        return combinations
    }

    private fun pushButton(button: Button, actualIndicatorLights: MutableList<Char>) {
        button.switches.forEach { switchIndex ->
            val current = actualIndicatorLights[switchIndex]
            val toSet = if (current == ON) OFF else ON
            actualIndicatorLights.removeAt(switchIndex)
            actualIndicatorLights.add(switchIndex, toSet)
        }
    }

    var current = 0

    fun calculateMinSwitchesJoltage(requirements: List<Int> = expectedJoltageRequirements, multiply: Int = 1): Int {
        current ++

        val unevenAsIndicatorLights = getUnevenAsIndicatorLights(requirements)
        val possibleButtonCombinations =
            findCombinations(unevenAsIndicatorLights).filter { requirements.doesNotGoNegativeIfPressed(it) }

        val min = possibleButtonCombinations.minOfOrNull { buttonsToPress ->
            val remainingJoltageRequirements = requirements.toMutableList()
            buttonsToPress.forEach { button ->
                remainingJoltageRequirements.decrease(button)
            }
            val buttonPresses = buttonsToPress.size * multiply
            if (remainingJoltageRequirements.all { it == 0 }) {
                buttonPresses
            } else {
                val newRemaininJoltageRequirements = remainingJoltageRequirements.map { it / 2 }
                val newMultiply = multiply * 2
                buttonPresses + calculateMinSwitchesJoltage(newRemaininJoltageRequirements, newMultiply)
            }
        }

        return min ?: 100_000 // if we found no possible combination, we return a big valuer to rule this one out
    }

    private fun getUnevenAsIndicatorLights(remainingJoltageRequirements: List<Int>): String =
        remainingJoltageRequirements.map { if (it % 2 == 0) OFF else ON }.joinToString("")

    private fun turnedOffIndicatorLights(): MutableList<Char> = expectedIndicatorLights.map { '.' }.toMutableList()

    private fun actualEqualsExpected(expectedIndicatorLights: String, actualIndicatorLights: List<Char>) =
        actualIndicatorLights.joinToString("") == expectedIndicatorLights
}

private fun List<Int>.doesNotGoNegativeIfPressed(buttons: List<Button>): Boolean {
    val copy = mutableListOf<Int>()
    copy.addAll(this)

    buttons.forEach { copy.decrease(it) }
    return copy.none { it < 0 }
}

data class Button(val switches: List<Int>)

fun MutableList<Int>.increase(button: Button) {
    this.plus(button, 1)
}

fun MutableList<Int>.decrease(button: Button) {
    this.plus(button, -1)
}


fun MutableList<Int>.plus(button: Button, number: Int) {
    button.switches.forEach { index ->
        val current = this.removeAt(index)
        this.add(index, current + number)
    }
}
