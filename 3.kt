val list: List<String> =  File("src/main/resources/daythree.txt").useLines { it.toList() }

fun main() {

    val stars: MutableMap<String, MutableSet<String>> = mutableMapOf() // Store a list of *'s and numbers they are adj to
    var count = 0

    for (lineIndex in list.indices) {
        val line = list[lineIndex]

        for (charIndex in line.indices) {
            val char = line[charIndex]

            if (char == '*') {
                val id = "$lineIndex$charIndex" // Unique id for each star
                if (!stars.containsKey(id)) stars[id] = mutableSetOf()

                // Searching 3x3 grid for numbers
                for (y in (lineIndex - 1)..(lineIndex + 1)) {
                    if (y < 0 || y >= list.size) continue

                    for (x in (charIndex - 1)..(charIndex + 1)) {
                        if (x < 0 || x >= line.length) continue

                        if (list[y][x].isDigit()) { // When number found
                            var startIndex = x; var endIndex = x

                            for (begin in x downTo 0) {
                                if (!list[y][begin].isDigit()) break
                                startIndex = begin
                            }
                            for (end in x until list[y].length) {
                                if (!list[y][end].isDigit()) break
                                endIndex = end
                            }

                            // Store location of the number in the format of line number, start and end index
                            stars[id]?.add("$y,$startIndex,$endIndex")
                        }

                    }
                }
            }
        }
    }

    for (star in stars) {
        if (star.value.size == 2) { // If it's adj to two numbers
            var power = 1
            for (pos in star.value) power *= getNumberFromPosition(pos) // Calc power
            count += power // Add power to count
        }
    }

    println(count)
}

fun getNumberFromPosition(position: String): Int { // Build number from its line num, start and end index
    val split = position.split(",")
    var number = ""
    for (i in split[1].toInt()..split[2].toInt()) number += list[split[0].toInt()][i]
    return number.toInt()
}
