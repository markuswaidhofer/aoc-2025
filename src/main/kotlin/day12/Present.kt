package day12

private const val TRUE = 1
private const val FALSE = 0

data class Present(val lines: List<String>) {
    fun size(): Int {
        return lines.flatMap { line ->
            line.map { if (it == '#') 1 else 0 }
        }.sum()
    }
}

data class Tree(val width: Int, val length: Int, val numberOfPresents: List<Int>){
    fun isEnoughSpace(presents: List<Present>): Int {
        val space = width * length
        val requiredSpace = numberOfPresents.mapIndexed { index, number ->
            presents[index].size() * number
        }.sum()
        return if (space >= requiredSpace) TRUE else FALSE
    }
}
