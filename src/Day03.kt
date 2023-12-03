fun main() {
    data class Coord(val x: Int, val y: Int)
    data class PotentialPart(val id: Int, val line: Int, val range: IntRange)

    fun scanPotentialParts(input: List<String>) = sequence {
        val numberRegex = Regex("\\d+")
        input.forEachIndexed { lineNr, line ->
            numberRegex.findAll(line).forEach { match ->
                yield(PotentialPart(match.value.toInt(), lineNr, match.range))
            }
        }
    }

    fun PotentialPart.surroundingCoordinates(input: List<String>) = sequence {
        val ranges = sequenceOf(
            line - 1 to (range.first - 1 .. range.last + 1),
            line to (range.first - 1).let { it .. it },
            line to (range.last + 1).let { it .. it },
            line + 1 to (range.first - 1 .. range.last + 1)
        )
        for ((lineNr, cells) in ranges) {
            val checkLine = input.getOrNull(lineNr) ?: continue
            for (cellNr in cells) {
                checkLine.getOrNull(cellNr) ?: continue
                yield(Coord(cellNr, lineNr))
            }
        }
    }

    fun part1(input: List<String>): Int {
        fun PotentialPart.isPartNumber(input: List<String>): Boolean {
            return this.surroundingCoordinates(input)
                .map { input[it.y][it.x] }
                .any { !it.isDigit() && it != '.' }
        }

        val actualParts = scanPotentialParts(input)
            .filter { it.isPartNumber(input) }
            .map { it.id }

        return actualParts.sum()
    }

    fun part2(input: List<String>): Int {
        fun PotentialPart.toGearOrNull(input: List<String>): Coord? {
            return surroundingCoordinates(input)
                .filter { (x, y) -> input[y][x] == '*' }
                .firstOrNull()
        }

        val partsByGear = scanPotentialParts(input)
            .groupBy { it.toGearOrNull(input) }
            .filterKeys { it != null }

        val gearRatios = partsByGear
            .filterValues { v -> v.size == 2 }
            .mapValues { (_, v) -> v[0].id * v[1].id }

        return gearRatios.values.sum()
    }

    check(part1(readInput("Day03_test")) == 4361)
    check(part2(readInput("Day03_test")) == 467835)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
