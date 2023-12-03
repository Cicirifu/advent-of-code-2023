fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            val firstDigit = it.find { it.isDigit() }!!.digitToInt()
            val secondDigit = it.findLast { it.isDigit() }!!.digitToInt()
            10 * firstDigit + secondDigit
        }
    }

    fun part2(input: List<String>): Int {
        val validStringlyDigits = mapOf(
            "1" to 1, "one" to 1,
            "2" to 2, "two" to 2,
            "3" to 3, "three" to 3,
            "4" to 4, "four" to 4,
            "5" to 5, "five" to 5,
            "6" to 6, "six" to 6,
            "7" to 7, "seven" to 7,
            "8" to 8, "eight" to 8,
            "9" to 9, "nine" to 9,
        )
        val digits = validStringlyDigits.keys

        return input.sumOf {
            val firstDigit = it.findAnyOf(digits)?.let { (_, match) -> validStringlyDigits[match] }!!
            val secondDigit = it.findLastAnyOf(digits)?.let { (_, match) -> validStringlyDigits[match] }!!
            10 * firstDigit + secondDigit
        }
    }

    check(part1(readInput("Day01_test")) == 142)
    check(part2(readInput("Day01_test2")) == 281)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
