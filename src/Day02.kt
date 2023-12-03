data class Sample(val red: Int, val green: Int, val blue: Int)
data class Game(val id: Int, val samples: List<Sample>)

object Parsers {
    fun game(input: String): Game {
        val split = input.split(Regex(": "))
        val id = split[0].removePrefix("Game ").toInt()

        return Game(id, samples(split[1]))

    }

    fun samples(input: String): List<Sample> {
        val split = input.split(Regex("; "))
        return split.map { sample(it) }
    }

    fun sample(input: String): Sample {
        val split = input.split(Regex(", "))
        val amounts = split.associate { amount(it) }
        return Sample(
            amounts["red"] ?: 0,
            amounts["green"] ?: 0,
            amounts["blue"] ?: 0
        )
    }

    fun amount(input: String): Pair<String, Int> {
        val split = input.split(" ")
        return split[1] to split[0].toInt()
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val games = input.map { Parsers.game(it) }
        val filteredGames = games.filter { game ->
            game.samples.all { sample ->
                sample.red <= 12 &&
                sample.green <= 13 &&
                sample.blue <= 14
            }
        }
        return filteredGames.sumOf { it.id }
    }

    fun part2(input: List<String>): Int {
        fun Sample.power() = maxOf(1, this.red) * maxOf(1, this.green) * maxOf(1, this.blue)

        val games = input.map { Parsers.game(it) }
        val minByGame = games.associateWith {
            it.samples.reduce { a, b ->
                Sample(maxOf(a.red, b.red), maxOf(a.green, b.green), maxOf(a.blue, b.blue))
            }
        }
        return minByGame.values.sumOf { it.power() }
    }

    check(part1(readInput("Day02_test")) == 8)
    check(part2(readInput("Day02_test")) == 2286)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
