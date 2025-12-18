package day11

private const val FFT = "fft"
private const val DAC = "dac"

fun main() {
    val devicesByName = createDevices("/inputs/day11/11puzzle.txt")

    val fromFftToDac = devicesByName[FFT]!!.countWaysTo(DAC)
    resetCaches(devicesByName)
    val fromDacToFft = devicesByName[DAC]!!.countWaysTo(FFT)
    resetCaches(devicesByName)

    println("fromFftToDac = $fromFftToDac / fromDacToFft = $fromDacToFft")

    val first = "svr"
    val second = if(fromFftToDac > 0) FFT else DAC
    val third = if(second == FFT) DAC else FFT
    val out = "out"

    val ways1 = devicesByName[first]!!.countWaysTo(second)
    resetCaches(devicesByName)
    val ways2 = devicesByName[second]!!.countWaysTo(third)
    resetCaches(devicesByName)
    val ways3 = devicesByName[third]!!.countWaysTo(out)
    resetCaches(devicesByName)
    val answer = ways1 * ways2 * ways3

    println("$ways1 * $ways2 * $ways3 = $answer")
    println("Answer is $answer")
}

fun resetCaches(devicesByName: Map<String, Device>) {
    devicesByName.values.forEach { it.cachedResult = null }
}
