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

    fun countWaysToOutRememberDacFft(dacVisited: Boolean, fftVisited: Boolean): Int {
        if(this.name == "out") {
            return if(dacVisited && fftVisited) 1 else 0
        }
        if(this.connectedDevices.isEmpty()) {
            return 0
        }
        return connectedDevices.sumOf { it.countWaysToOutRememberDacFft(dacVisited || it.name == "dac", fftVisited || it.name == "fft") }
    }


}
