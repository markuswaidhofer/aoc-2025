package day11

data class Device(val name: String) {
    val connectedDevices = mutableListOf<Device>()

    var cachedResult: Long? = null

    fun connect(toConnect: List<Device>) {
        connectedDevices.addAll(toConnect)
    }

    fun countWaysTo(goal: String): Long {
        if (cachedResult == null) {
            cachedResult = if (this.name == goal) {
                1L
            } else if (this.connectedDevices.isEmpty()) {
                0L
            } else connectedDevices.sumOf { it.countWaysTo(goal) }
        }
        return cachedResult!!
    }

}

// fromFftToDac = 1 / fromDacToFft = 0
// 1 * 1 * 2 = 2
// Answer is 2
