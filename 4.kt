val list: List<String> =  File("src/main/resources/dayfour.txt").useLines { it.toList() }
var count = 0

fun main() {

    val games: LinkedHashMap<Pair<List<Int>, List<Int>>, Int> = linkedMapOf() // Store cards and the # of instances there are

    for (line in list) {
        val data = line.split(":")[1] // Removing "Game X:" prefix

        val winningNumbersList = data.split("|")[0].replace("  ", " ").split(" ")
        val numbersList = data.split("|")[1].replace("  ", " ").split(" ")

        val pair = winningNumbersList.mapNotNull { it.toIntOrNull() } to numbersList.mapNotNull { it.toIntOrNull() }
        games[pair] = 1
    }

    for (x in games.entries.withIndex()){
        val game = x.value.key

        for (i in 0 until x.value.value) { // Repeat for every instance of card

            var winningNums = 0
            for (winningNumber in game.first) {
                if (game.second.contains(winningNumber)) winningNums++ // Add total of winning numbers
            }
            for (i in 1..winningNums) { // Duplicate nth card
                games.entries.elementAtOrNull(x.index + i)?.let {
                    it.setValue(it.value + 1)
                }
            }

        }
    }

    for (value in games.values) count += value
    println(count)
}
