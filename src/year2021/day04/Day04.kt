package year2021.day04

import utils.PuzzleInput
import utils.checkWithOutput
import utils.columns
import utils.rows

fun main() {
    val puzzleInput = PuzzleInput(2021, 4)

    checkWithOutput(day04_part1(puzzleInput.test), 4512)
    checkWithOutput(day04_part2(puzzleInput.test), 1924)

    println(day04_part1(puzzleInput.real))
    println(day04_part2(puzzleInput.real))
}

private fun getDrawnNumbers(input: List<String>): List<Int> {
    return input.first().split(',').map(String::toInt)
}

private fun getBoards(input: List<String>): List<Board> {
    return input
        .drop(2)
        .windowed(5, 6, false)
        .map { list ->
            list
                .map { line ->
                    line
                        .split(' ')
                        .filter(String::isNotEmpty)
                        .map(String::toInt)
                }
                .flatten()
        }
        .map(::Board)
}

private class Board(val numbers: List<Int>) {
    private val horizontalLines = numbers.rows(5)
    private val verticalLines = numbers.columns(5)

    private fun List<List<Int>>.checkLinesForWin(drawnNumbers: List<Int>): Boolean =
        any { line -> line.all { number -> number in drawnNumbers } }

    fun checkForWin(drawnNumbers: List<Int>): Boolean {
        return horizontalLines.checkLinesForWin(drawnNumbers) || verticalLines.checkLinesForWin(drawnNumbers)
    }
}

private fun day04_part1(input: List<String>): Int {
    return solve(input, findFirst = true)
}

private fun day04_part2(input: List<String>): Int {
    return solve(input, findFirst = false)
}

private fun solve(input: List<String>, findFirst: Boolean): Int {
    val numbers = getDrawnNumbers(input)
    val boards = getBoards(input)
    val boardWinOrder = getBoardWinOrder(numbers, boards)
    val (winningBoard, winningNumbers) = if (findFirst) boardWinOrder.first() else boardWinOrder.last()
    return winningBoard.numbers.filterNot { it in winningNumbers }.sum() * winningNumbers.last()
}

private fun getBoardWinOrder(numbers: List<Int>, boards: List<Board>): List<Pair<Board, List<Int>>> {
    val boardWinOrder = mutableListOf<Pair<Board, List<Int>>>()
    val drawnNumbers = mutableListOf<Int>()

    numbers.fold(boards) { remainingBoards, nextNumber ->
        if (remainingBoards.isEmpty()) return@fold emptyList()
        drawnNumbers.add(nextNumber)

        remainingBoards.filter { board ->
            if (board.checkForWin(drawnNumbers)) {
                boardWinOrder.add(board to drawnNumbers.toList())
                false
            } else true
        }
    }

    return boardWinOrder
}