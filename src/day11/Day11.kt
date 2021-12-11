package day11

import print
import readInput

fun main() {
    val input = readInput("Day11").map { it.map { char -> "$char".toInt() } } as MutableList<MutableList<Int>>
    val testInput = mutableListOf(
        mutableListOf(5, 4, 8, 3, 1, 4, 3, 2, 2, 3),
        mutableListOf(2, 7, 4, 5, 8, 5, 4, 7, 1, 1),
        mutableListOf(5, 2, 6, 4, 5, 5, 6, 1, 7, 3),
        mutableListOf(6, 1, 4, 1, 3, 3, 6, 1, 4, 6),
        mutableListOf(6, 3, 5, 7, 3, 8, 5, 4, 7, 8),
        mutableListOf(4, 1, 6, 7, 5, 2, 4, 6, 4, 5),
        mutableListOf(2, 1, 7, 6, 8, 4, 1, 7, 2, 1),
        mutableListOf(6, 8, 8, 2, 8, 8, 1, 1, 3, 4),
        mutableListOf(4, 8, 4, 6, 8, 4, 8, 5, 5, 4),
        mutableListOf(5, 2, 8, 3, 7, 5, 1, 5, 2, 6),
    )

    val smallTestInput = mutableListOf(
        mutableListOf(1, 1, 1, 1, 1),
        mutableListOf(1, 9, 9, 9, 1),
        mutableListOf(1, 9, 1, 9, 1),
        mutableListOf(1, 9, 9, 9, 1),
        mutableListOf(1, 1, 1, 1, 1),
    )

//    part1(input)
    part2(input)
}

fun part1(input: MutableList<MutableList<Int>>) {
    input.print()
    var totalFlashes = 0
    // Steps:
    repeat(100) {
        println("Step: ${it + 1}")
        val flashedThisStep = hashSetOf<Pair<Int, Int>>()
        for (i in input.indices) {
            for (j in input[0].indices) {
                if (flashedThisStep.contains(Pair(i, j))) continue

                input[i][j]++

                if (input[i][j] > 9) {
                    val toFlash = mutableListOf<Pair<Int, Int>>()
                    toFlash.add(Pair(i, j))
                    while (toFlash.isNotEmpty()) {
                        val currPair = toFlash.removeAt(0)
                        if (flashedThisStep.contains(currPair)) continue
                        flashedThisStep.add(currPair)
                        totalFlashes++
                        val x = currPair.first
                        val y = currPair.second

                        // Top-Left
                        if (x - 1 >= 0 && y - 1 >= 0 && !flashedThisStep.contains(Pair(x - 1, y - 1))) {
                            input[x - 1][y - 1]++
                            if (input[x - 1][y - 1] > 9) toFlash.add(Pair(x - 1, y - 1))
                        }
                        // Bottom-Left
                        if (x + 1 < input[0].size && y - 1 >= 0 && !flashedThisStep.contains(Pair(x + 1, y - 1))) {
                            input[x + 1][y - 1]++
                            if (input[x + 1][y - 1] > 9) toFlash.add(Pair(x + 1, y - 1))
                        }
                        // Top-Right
                        if (x - 1 >= 0 && y + 1 < input.size && !flashedThisStep.contains(Pair(x - 1, y + 1))) {
                            input[x - 1][y + 1]++
                            if (input[x - 1][y + 1] > 9) toFlash.add(Pair(x - 1, y + 1))
                        }
                        // Bottom-Right
                        if (x + 1 < input[0].size && y + 1 < input.size && !flashedThisStep.contains(
                                Pair(
                                    x + 1,
                                    y + 1
                                )
                            )
                        ) {
                            input[x + 1][y + 1]++
                            if (input[x + 1][y + 1] > 9) toFlash.add(Pair(x + 1, y + 1))
                        }

                        // Up
                        if (x - 1 >= 0 && !flashedThisStep.contains(Pair(x - 1, y))) {
                            input[x - 1][y]++
                            if (input[x - 1][y] > 9) toFlash.add(Pair(x - 1, y))
                        }

                        // Down
                        if (x + 1 < input[0].size && !flashedThisStep.contains(Pair(x + 1, y))) {
                            input[x + 1][y]++
                            if (input[x + 1][y] > 9) toFlash.add(Pair(x + 1, y))
                        }

                        // Left
                        if (y - 1 >= 0 && !flashedThisStep.contains(Pair(x, y - 1))) {
                            input[x][y - 1]++
                            if (input[x][y - 1] > 9) toFlash.add(Pair(x, y - 1))
                        }

                        // Right
                        if (y + 1 < input[0].size && !flashedThisStep.contains(Pair(x, y + 1))) {
                            input[x][y + 1]++
                            if (input[x][y + 1] > 9) toFlash.add(Pair(x, y + 1))
                        }
                    }
                }
            }
        }

        flashedThisStep.forEach { item -> input[item.first][item.second] = 0 }
        input.print()
    }

    println("Total flashes: $totalFlashes")
}

fun part2(input: MutableList<MutableList<Int>>) {
    var step = 1
    // Steps:
    while (true) {
        val flashedThisStep = hashSetOf<Pair<Int, Int>>()
        for (i in input.indices) {
            for (j in input[0].indices) {
                if (flashedThisStep.contains(Pair(i, j))) continue

                input[i][j]++

                if (input[i][j] > 9) {
                    val toFlash = mutableListOf<Pair<Int, Int>>()
                    toFlash.add(Pair(i, j))
                    while (toFlash.isNotEmpty()) {
                        val currPair = toFlash.removeAt(0)
                        if (flashedThisStep.contains(currPair)) continue
                        flashedThisStep.add(currPair)
                        val x = currPair.first
                        val y = currPair.second

                        // Top-Left
                        if (x - 1 >= 0 && y - 1 >= 0 && !flashedThisStep.contains(Pair(x - 1, y - 1))) {
                            input[x - 1][y - 1]++
                            if (input[x - 1][y - 1] > 9) toFlash.add(Pair(x - 1, y - 1))
                        }
                        // Bottom-Left
                        if (x + 1 < input[0].size && y - 1 >= 0 && !flashedThisStep.contains(Pair(x + 1, y - 1))) {
                            input[x + 1][y - 1]++
                            if (input[x + 1][y - 1] > 9) toFlash.add(Pair(x + 1, y - 1))
                        }
                        // Top-Right
                        if (x - 1 >= 0 && y + 1 < input.size && !flashedThisStep.contains(Pair(x - 1, y + 1))) {
                            input[x - 1][y + 1]++
                            if (input[x - 1][y + 1] > 9) toFlash.add(Pair(x - 1, y + 1))
                        }
                        // Bottom-Right
                        if (x + 1 < input[0].size && y + 1 < input.size && !flashedThisStep.contains(
                                Pair(
                                    x + 1,
                                    y + 1
                                )
                            )
                        ) {
                            input[x + 1][y + 1]++
                            if (input[x + 1][y + 1] > 9) toFlash.add(Pair(x + 1, y + 1))
                        }

                        // Up
                        if (x - 1 >= 0 && !flashedThisStep.contains(Pair(x - 1, y))) {
                            input[x - 1][y]++
                            if (input[x - 1][y] > 9) toFlash.add(Pair(x - 1, y))
                        }

                        // Down
                        if (x + 1 < input[0].size && !flashedThisStep.contains(Pair(x + 1, y))) {
                            input[x + 1][y]++
                            if (input[x + 1][y] > 9) toFlash.add(Pair(x + 1, y))
                        }

                        // Left
                        if (y - 1 >= 0 && !flashedThisStep.contains(Pair(x, y - 1))) {
                            input[x][y - 1]++
                            if (input[x][y - 1] > 9) toFlash.add(Pair(x, y - 1))
                        }

                        // Right
                        if (y + 1 < input[0].size && !flashedThisStep.contains(Pair(x, y + 1))) {
                            input[x][y + 1]++
                            if (input[x][y + 1] > 9) toFlash.add(Pair(x, y + 1))
                        }
                    }
                }
            }
        }

        flashedThisStep.forEach { item -> input[item.first][item.second] = 0 }
        if (flashedThisStep.size == input.size * input[0].size) break

        step++
    }

    println("All flashed on step: $step")
}

