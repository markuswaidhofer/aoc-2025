package day10

import kotlin.math.pow

private const val ON = '#'
private const val OFF = '.'

class Machine(val expectedIndicatorLights: String, val expectedJoltageRequirements: List<Int>, val buttons: List<Button>) {

    var minJoltageButtonPresses: Int = Int.MAX_VALUE

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

            if (actualEqualsExpected(actualIndicatorLights) && buttonPresses < min) {
                min = buttonPresses
            }

            combination++
        }

        return min
    }

    fun calculateMinSwitchesJoltage(): Int {
        val currentJoltages = expectedIndicatorLights.map { 0 }.toMutableList()

        val currentButton = 0
        val buttonPresses = 0
        // println("Goal: " + expectedJoltageRequirements)
        pressButton(currentButton, buttonPresses, currentJoltages)

        return minJoltageButtonPresses
    }

    fun pressButton(currentButtonIndex: Int, totalButtonPresses: Int, currentJoltages: MutableList<Int>) {
        val currentButton = buttons[currentButtonIndex]
        // println("Handling button $currentButtonIndex = $currentButton")
        // println("We are at $currentJoltages")

        // repeat pushing the button until no longer possible
        var currentButtonPresses = 0
        var newTotalButtonPresses = totalButtonPresses
        while (!joltagesExceeded(currentJoltages)) {
            currentJoltages.increase(currentButton)
            newTotalButtonPresses++
            currentButtonPresses++
            // println("Tapped button and now we are at $currentJoltages")

            if (currentJoltages == expectedJoltageRequirements && newTotalButtonPresses < minJoltageButtonPresses) {
                minJoltageButtonPresses = newTotalButtonPresses
                // println("Found solution, new minimum = $newTotalButtonPresses")
            }
        }

        // remove one button press of current and move to next button
        while (currentButtonPresses > 0) {
            currentJoltages.decrease(currentButton)
            // println("Removed button and now we are at $currentJoltages")
            newTotalButtonPresses--
            currentButtonPresses--
            if (currentButtonIndex + 1 < buttons.size) { // there is a next button
                pressButton(currentButtonIndex + 1, newTotalButtonPresses, currentJoltages)
            }
        }

        // println("Done with $currentButtonIndex")
    }

    private fun joltagesExceeded(currentJoltages: MutableList<Int>): Boolean =
        currentJoltages.mapIndexed { index, joltage -> joltage > expectedJoltageRequirements[index] }.any { it }

    private fun pushButton(button: Button, actualIndicatorLights: MutableList<Char>) {
        button.switches.forEach { switchIndex ->
            val current = actualIndicatorLights[switchIndex]
            val toSet = if (current == ON) OFF else ON
            actualIndicatorLights.removeAt(switchIndex)
            actualIndicatorLights.add(switchIndex, toSet)
        }
    }

    private fun turnedOffIndicatorLights(): MutableList<Char> = expectedIndicatorLights.map { '.' }.toMutableList()

    private fun actualEqualsExpected(actualIndicatorLights: List<Char>) = actualIndicatorLights.joinToString("") == expectedIndicatorLights
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
