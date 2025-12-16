package day10

import kotlin.math.pow

private const val ON = '#'
private const val OFF = '.'

class Machine(val expectedIndicatorLights: String, val buttons: List<Button>) {

    fun calculateMinSwitches(): Int {
        var min = Int.MAX_VALUE
        val possibleCombinations = 2.0.pow(buttons.size).toInt()

        var combination = 0
        while (combination < possibleCombinations) {
            val actualIndicatorLights: MutableList<Char> = turnedOffIndicatorLights()

            var buttonPresses = 0
            val useButton = List(buttons.size) { (combination shr it) and 1 == 1 }
            buttons.forEachIndexed { buttonIndex, button ->
                if(useButton[buttonIndex]) {
                    pushButton(button, actualIndicatorLights)
                    buttonPresses++
                }
            }

            if(actualEqualsExpected(actualIndicatorLights) && buttonPresses < min) {
                min = buttonPresses
            }

            combination++
        }

        return min
    }

    private fun pushButton(button: Button, actualIndicatorLights: MutableList<Char>) {
        button.switches.forEach { switchIndex ->
            val current = actualIndicatorLights[switchIndex]
            val toSet = if(current == ON) OFF else ON
            actualIndicatorLights.removeAt(switchIndex)
            actualIndicatorLights.add(switchIndex, toSet)
        }
    }

    private fun turnedOffIndicatorLights(): MutableList<Char> = expectedIndicatorLights.map { '.' }.toMutableList()

    private fun actualEqualsExpected(actualIndicatorLights: List<Char>) = actualIndicatorLights.joinToString("") == expectedIndicatorLights
}

data class Button(val switches: List<Int>)
