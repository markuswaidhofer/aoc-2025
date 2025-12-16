package day10

import kotlin.math.pow

private const val ON = '#'
private const val OFF = '.'

class Machine(val expectedIndicatorLights: String, val expectedJoltageRequirements: List<Int>, val buttons: List<Button>) {

    var minJoltageButtonPresses: Int = Int.MAX_VALUE
    val maxJoltageButtonPresses = expectedJoltageRequirements.max()


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

    fun calculateMinSwitchesJoltage(): Int {
        val currentJoltages = expectedIndicatorLights.map { 0 }.toMutableList()

        val currentButton = 0
        val buttonPresses = 0
        pressButton(currentButton, buttonPresses, currentJoltages)

        return minJoltageButtonPresses
    }

    fun pressButton(currentButton: Int, buttonPresses: Int, currentJoltages: MutableList<Int>) {
        currentJoltages.increase(buttons[currentButton])
        val newButtonPresses = buttonPresses + 1

        if(currentJoltages == expectedJoltageRequirements && newButtonPresses < minJoltageButtonPresses) {
            minJoltageButtonPresses = newButtonPresses
        }

        // TODO continue here - basically, i need to recursively press buttons. keep pushing the same button. if it went over the
        // maximum, we are in decreasing mode. decrease by one and move to the next button. if decreasing hits zero, go to previous button
        // and continue that by one. continue until there is no button left to decrease (i.e. decreasing the first button will end the recursion)

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
