package year2020.day11

import utils.PuzzleInput
import utils.checkWithOutput

fun main() {
    val puzzleInput = PuzzleInput(2020, 11) { input ->
        input.map { line -> line.map(Seat::of) }.let(::SeatPlan)
    }

    checkWithOutput(day11_part1(puzzleInput.tests[0]), 37)
    checkWithOutput(day11_part2(puzzleInput.tests[0]), 1)

//    println(day11_part1(puzzleInput.real))
//    println(day11_part2(puzzleInput.real))
}

enum class Seat(val char: Char) {
    NONE('.'),
    EMPTY('L'),
    OCCUPIED('#');

    companion object {
        private val valuesByChar = values().associateBy { it.char }
        fun of(char: Char) = valuesByChar[char] ?: error("Unknown seat type: $char")
    }
}

data class SeatPlan(val seats: List<List<Seat>>) {
    val height = seats.count()
    val width = seats.firstOrNull()?.count() ?: 0

    fun count(seatType: Seat): Int = seats.sumOf { row -> row.count { seat -> seat == seatType } }

    fun advance(): SeatPlan {
        return seats.mapIndexed { y, row ->
            row.mapIndexed { x, seat ->
                val adjacentSeats = getAdjacentSeats(x, y)
                when {
                    seat == Seat.EMPTY && adjacentSeats.all { it != Seat.OCCUPIED } -> Seat.OCCUPIED
                    seat == Seat.OCCUPIED && adjacentSeats.count { it == Seat.OCCUPIED } >= 4 -> Seat.EMPTY
                    else -> seat
                }
            }
        }.let(::SeatPlan)
    }

    fun getAdjacentSeats(x: Int, y: Int): List<Seat> {
        return ((x-1)..(x+1)).flatMap { dx ->
            ((y-1)..(y+1)).mapNotNull { dy ->
                when {
                    dx == x && dy == y -> null
                    dx < 0 || dx >= width -> null
                    dy < 0 || dy >= height -> null
                    else -> seats[dy][dx]
                }
            }
        }
    }

    fun print() {
        seats.joinToString("\n") { row ->
            row.map(Seat::char).joinToString("")
        }.let(::println)
    }
}

private fun day11_part1(seatPlan: SeatPlan): Int {
    var old = SeatPlan(emptyList())
    var new = seatPlan.copy()

    while (old != new) {
        old = new.copy()
        new = new.advance()

//        new.print()
//        println()
    }

    return new.count(Seat.OCCUPIED)
}

private fun day11_part2(seatPlan: SeatPlan): Int {
    return 0
}