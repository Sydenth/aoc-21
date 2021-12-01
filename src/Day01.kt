fun main() {
    fun countIncreases(input: List<Int>): Int {
        return input
            .zipWithNext { prev, next -> next > prev }
            .count { it }
    }

    fun part1(input: List<Int>): Int {
        return countIncreases(input)
    }

    fun part2(input: List<Int>): Int {
        val transformed = input
            .windowed(
                size = 3,
                step = 1,
                partialWindows = false
            ) { window ->
                window.sum()
            }
        return countIncreases(transformed)
    }

    val input = readInput("Day01").map(String::toInt)
    println(part1(input))
    println(part2(input))
}
