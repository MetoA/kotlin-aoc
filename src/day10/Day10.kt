package day10

import readInput

fun main() {
    val input = readInput("Day10")
    val testInput = listOf(
        "[({(<(())[]>[[{[]{<()<>>",
        "[(()[<>])]({[<{<<[]>>(",
        "{([(<{}[<>[]}>{[]{[(<()>",
        "(((({<>}<{<{<>}{[]{[]{}",
        "[[<[([]))<([[{}[[()]]]",
        "[{[{({}]{}}([{[{{{}}([]",
        "{<[[]]>}<{[{[{[]{()[[[]",
        "[<(<(<(<{}))><([]([]()",
        "<{([([[(<>()){}]>(<<{{",
        "<{([{{}}[<[[[<>{}]]]>[]]"
    )

    part1(input)
    part2(input)
}

fun part1(input: List<String>) {
    val matchingParts = mapOf(
        ')' to '(',
        ']' to '[',
        '}' to '{',
        '>' to '<'
    )

    val corruptedValues = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137
    )

    val corruptedCharacters = mutableListOf<Char>()

    for (line in input) {
        val stack = mutableListOf<Char>()

        for (char in line) {
            if (char.isOpening()) stack.push(char)
            if (char.isClosing()) {
                if (stack.peek() == matchingParts[char]) {
                    stack.pop()
                } else {
                    corruptedCharacters.add(char)
                    break
                }
            }
        }
    }

    println(corruptedCharacters.fold(0) { acc, char -> acc + corruptedValues[char]!! })
}

fun part2(input: List<String>) {
    val matchingParts = mapOf(
        '(' to ')',
        '[' to ']',
        '{' to '}',
        '<' to '>',
        ')' to '(',
        ']' to '[',
        '}' to '{',
        '>' to '<'
    )

    val closingValues = mapOf(
        ')' to 1,
        ']' to 2,
        '}' to 3,
        '>' to 4
    )

    fun completeLine(line: List<Char>): List<Char> {
        val result = mutableListOf<Char>()

        line.reversed().forEach { result.add(matchingParts[it]!!) }

        return result
    }

    val incompleteLines = mutableListOf<List<Char>>()
    val completedLines = mutableListOf<List<Char>>()

    for (line in input) {
        val stack = mutableListOf<Char>()
        var corrupted = false;

        for (char in line) {
            if (char.isOpening()) stack.push(char)
            if (char.isClosing()) {
                if (stack.peek() == matchingParts[char]) stack.pop()
                else { corrupted = true; break}
            }
        }

        if (corrupted) continue

        if (stack.size != 0) incompleteLines.add(stack)
    }

    incompleteLines.forEach { completedLines.add(completeLine(it)) }
    val results = completedLines.map { it.fold(0L) { acc, char -> acc * 5 + closingValues[char]!!} }.sorted()
    println("${results[completedLines.size / 2]} result")
}

fun Char.isOpening() = this in listOf('[', '(', '{', '<')
fun Char.isClosing() = this in listOf(']', ')', '}', '>')

fun <T> MutableList<T>.push(item: T) = this.add(item)
fun <T> MutableList<T>.pop(): T? = if (this.size == 0) null else this.removeAt(this.size - 1)
fun <T> MutableList<T>.peek(): T? = this.lastOrNull()
