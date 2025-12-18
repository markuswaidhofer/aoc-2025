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

    fun calculateMinSwitchesJoltage(): Int {
        var buttonPresses = 0
        var multiply = 1
        var remainingJoltageRequirements = expectedJoltageRequirements.toMutableList()

        while(remainingJoltageRequirements.any { it != 0 }) {
            val unevenAsIndicatorLights = getUnevenAsIndicatorLights(remainingJoltageRequirements)
            val possibleButtonCombinations = findCombinations(unevenAsIndicatorLights)

            val buttonsToPress = possibleButtonCombinations.first { remainingJoltageRequirements.doesNotGoNegativeIfPressed(it) }
            buttonsToPress.forEach { button ->
                remainingJoltageRequirements.decrease(button)
            }
            buttonPresses += buttonsToPress.size * multiply


            multiply *= 2
            remainingJoltageRequirements = remainingJoltageRequirements.map { it / 2 }.toMutableList()
        }

        return buttonPresses
    }

    private fun getUnevenAsIndicatorLights(remainingJoltageRequirements: MutableList<Int>): String =
        remainingJoltageRequirements.map { if (it % 2 == 0) OFF else ON }.joinToString("")

    private fun turnedOffIndicatorLights(): MutableList<Char> = expectedIndicatorLights.map { '.' }.toMutableList()

    private fun actualEqualsExpected(expectedIndicatorLights: String, actualIndicatorLights: List<Char>) = actualIndicatorLights.joinToString("") == expectedIndicatorLights
}

private fun MutableList<Int>.doesNotGoNegativeIfPressed(buttons: List<Button>): Boolean {
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
