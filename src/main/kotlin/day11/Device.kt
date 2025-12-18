package day11

data class Device(val name: String){
    val connectedDevices = mutableListOf<Device>()

    fun connect(toConnect: List<Device>) {
        connectedDevices.addAll(toConnect)
    }

    fun countWaysToOut(): Int {
        if(this.name == "out") {
            return 1
        }
        if(this.connectedDevices.isEmpty()) {
            return 0
        }
        return connectedDevices.sumOf { it.countWaysToOut() }
    }
}