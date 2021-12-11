package day09

import readInput

fun main() {
    val input = readInput("Day09")
    val inputTest = listOf(
        "2199943210",
        "3987894921",
        "9856789892",
        "8767896789",
        "9899965678"
    ).map { line -> line.map { char -> "$char".toInt() } }
    val input2 = input.map { line -> line.map { char -> "$char".toInt() } }
//    part1(input)
    part2(input2)

    part2_again(input2)
}

fun part1(input: List<String>) {
    var res = 0

    for ((i, line) in input.withIndex()) {
        for ((j, _) in line.withIndex()) {
            val currNum = "${input[i][j]}".toInt()

            if (i - 1 >= 0 && "${input[i - 1][j]}".toInt() <= currNum) continue

            if (i + 1 < input.size && "${input[i + 1][j]}".toInt() <= currNum) continue

            if (j - 1 >= 0 && "${input[i][j - 1]}".toInt() <= currNum) continue

            if (j + 1 < line.length && "${input[i][j + 1]}".toInt() <= currNum) continue

            res += currNum + 1
        }
    }

    println(res)
}

// Not correct
fun part2(input: List<List<Int>>) {
    val visited = hashSetOf<String>()
    val allBasin = mutableListOf<Int>()

    fun countBasin(x: Int, y: Int): Int {
        var res = 0
        val queue = mutableListOf<Coord>()
        queue.add(Coord(x, y))
        if (visited.contains("$x,$y")) {
            return 0
        }

        do {
            val coord = queue.removeAt(0)
            val i = coord.x
            val j = coord.y
            val num = input[i][j]
            res++

            if (i - 1 >= 0 && (input[i - 1][j] == num + 1 || input[i - 1][j] == num - 1) && !visited.contains("${i - 1},$j")) queue.add(
                Coord(i - 1, j)
            )
            if (i + 1 < input.size && (input[i + 1][j] == num + 1 || input[i + 1][j] == num - 1) && !visited.contains("${i + 1},$j")) queue.add(
                Coord(i + 1, j)
            )
            if (j - 1 >= 0 && (input[i][j - 1] == num + 1 || input[i][j - 1] == num - 1) && !visited.contains("$i,${j - 1}")) queue.add(
                Coord(i, j - 1)
            )
            if (j + 1 < input[0].size && (input[i][j + 1] == num + 1 || input[i][j + 1] == num - 1) && !visited.contains(
                    "$i,${j + 1}"
                )
            ) queue.add(Coord(i, j + 1))
            visited.add("${coord.x},${coord.y}")
        } while (queue.size != 0)

        return res
    }

    for (i in input.indices) {
        for (j in input[0].indices) {
            val basin = countBasin(i, j)
            if (basin > 1) allBasin.add(basin)
        }
    }

    println(allBasin)
}

// Not correct
fun part2_again(input: List<List<Int>>) {
    val visited = hashSetOf<Pair<Int, Int>>()
    val allBasin = mutableListOf<Int>()

    // traversal
    for (i in input.indices) {
        for (j in input[0].indices) {
            // dont check 9 and visited elements
            if (input[i][j] == 9) continue
            if (visited.contains(Pair(i, j))) continue
            var basinSize = 0

            val queue = mutableListOf<Coord>()
            queue.add(Coord(i, j))

            while (queue.size != 0) {
                val coord = queue.removeAt(0)

                val x = coord.x
                val y = coord.y
                if (visited.contains(Pair(x, y))) continue
                // keep track of visited
                visited.add(Pair(x, y))

                basinSize++
                val num = input[x][y]
                if (x-1 >= 0 && input[x-1][y] != 9 && !visited.contains(Pair(x-1, y)) && (input[x-1][y] == num + 1 || input[x-1][y] == num - 1)) {
                    queue.add(Coord(x-1, y))
                }

                if (x+1 < input.size && input[x+1][y] != 9 && !visited.contains(Pair(x+1, y)) && (input[x+1][y] == num + 1 || input[x+1][y] == num - 1)) {
                    queue.add(Coord(x+1, y))
                }

                if (y-1 >= 0 && input[x][y-1] != 9 && !visited.contains(Pair(x, y-1)) && (input[x][y-1] == num + 1 || input[x][y-1] == num - 1)) {
                    queue.add(Coord(x, y-1))
                }

                if (y+1 < input[0].size && input[x][y+1] != 9 && !visited.contains(Pair(x, y+1)) && (input[x][y+1] == num + 1 || input[x][y+1] == num - 1)) {
                    queue.add(Coord(x, y+1))
                }

            }
            if (basinSize > 1) allBasin.add(basinSize)
        }
    }

    println("$allBasin all basin")
    println(allBasin.sortedDescending().take(3).multiply())
}

data class Coord(val x: Int, val y: Int)

fun List<Int>.multiply() = this.fold(1) { acc, num -> acc * num}

