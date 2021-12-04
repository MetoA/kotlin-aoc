package day04

import readInput

fun main() {
    val input = readInput("Day04")
    val numbers = input[0].split(",").map { it.toInt() }

    val winningPositions = listOf(
        listOf(0, 1, 2, 3, 4),
        listOf(5, 6, 7, 8, 9),
        listOf(10, 11, 12, 13, 14),
        listOf(15, 16, 17, 18, 19),
        listOf(20, 21, 22, 23, 24),
        listOf(0, 5, 10, 15, 20),
        listOf(1, 6, 11, 16, 21),
        listOf(2, 7, 12, 17, 22),
        listOf(3, 8, 13, 18, 23),
        listOf(4, 9, 14, 19, 24),
    )

    println(part1(input, numbers, winningPositions))
    println(part2(input, numbers, winningPositions))

}

fun part1(input: List<String>, nums: List<Int>, winningPos: List<List<Int>>): Int {
    val boards = makeBoards(input.drop(2))
    for ((i, num) in nums.withIndex()) {
        for (board in boards) {
            board.draw(num)
            if (i >= 5) {
                if (board.checkIfIsComplete(winningPos)) {
                    return board.sumOfAllUnmarkedNumbers() * num
                }
            }
        }
    }

    return 0
}

fun part2(input: List<String>, nums: List<Int>, winningPos: List<List<Int>>): Int {
    val boards = makeBoards(input.drop(2))
    val winningBoards = mutableListOf<Board>()
    var lastNum = 0
    for ((i, num) in nums.withIndex()) {
        for (board in boards.reversed()) {
            board.draw(num)
            if (i >= 5) {
                if (board.checkIfIsComplete(winningPos)) {
                    lastNum = num
                    winningBoards.add(board)
                    boards.remove(board)
                }
            }
        }
    }

    return winningBoards.last().sumOfAllUnmarkedNumbers() * lastNum
}

private fun makeBoards(input: List<String>): MutableList<Board> {
    val SPLIT = Regex("\\s+")
    val boards = mutableListOf<Board>()
    var board = Board(mutableListOf(), mutableListOf())
    for (line in input) {
        if (line.isBlank()) {
            boards.add(board)
            board = Board(mutableListOf(), mutableListOf())
            continue
        }

        line.trim().split(SPLIT).map { it.toInt() }.forEach { board.elements.add(it) }
    }
    boards.add(board)

    return boards
}


data class Board(val elements: MutableList<Int>, val drawn: MutableList<Int>) {
    fun draw(num: Int) {
        if (elements.contains(num)) drawn.add(elements.indexOf(num))
    }

    fun checkIfIsComplete(winningPos: List<List<Int>>): Boolean {
        for (pos in winningPos) {
            if (pos.intersect(drawn.toSet()).size == 5) {
                return true
            }
        }

        return false
    }

    fun sumOfAllUnmarkedNumbers() = ((0..24).toList() - drawn.toSet()).fold(0) {acc, value -> acc + elements[value]}
}