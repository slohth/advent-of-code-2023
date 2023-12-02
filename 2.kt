fun main() {
    val list: List<String> =  File("src/main/resources/daytwo.txt").useLines { it.toList() }
    val results: MutableMap<Int, List<GameResult>> = mutableMapOf()

    for (str in list) {
        val gameId = str.split(":")[0].split(" ")[1].toInt() // "Game 13: x" -> "13"
        val gameResults = str.split(":")[1] // Removing the "Game ID:" prefix

        val resultsList: MutableList<GameResult> = mutableListOf()
        for (game in gameResults.split(";")) { // For each set in the game

            val gr = GameResult(0, 0, 0)

            val split = game.split(" ")
            for (i in split.indices) {

                split[i].toIntOrNull()?.let { // If a valid number
                    val color = split[i + 1] // Next sequential string
                    when {
                        color.contains("red") -> gr.red = it
                        color.contains("green") -> gr.green = it
                        color.contains("blue") -> gr.blue = it
                    }
                }

            }
            resultsList.add(gr)
        }
        results[gameId] = resultsList
    }

    var count = 0
    for (entry in results) {

        var highestRed = 0; var highestGreen = 0; var highestBlue = 0
        for (result in entry.value) {
            if (result.red > highestRed) highestRed = result.red
            if (result.green > highestGreen) highestGreen = result.green
            if (result.blue > highestBlue) highestBlue = result.blue
        }
        count += highestRed * highestGreen * highestBlue

    }

    println(count)
}

data class GameResult(var red: Int, var green: Int, var blue: Int)
