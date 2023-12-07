val list: List<String> =  File("src/main/resources/dayseven.txt").useLines { it.toList() }
val types = "J23456789TQKA"
var count = 0L

fun main() {
    val data: MutableList<Pair<String, Int>> = mutableListOf() // Store the card draw and bet

    for (line in list) {
        val split = line.split(" ")
        data.add(split[0] to split[1].toInt())
    }

    val handComparator = Comparator<Pair<String, Int>> { first, second ->
        var firstScore = calcPartTwoScore(first.first)
        var secondScore = calcPartTwoScore(second.first)

        if (firstScore != secondScore) return@Comparator firstScore - secondScore

        for (i in 0 until 5) {
            firstScore = types.indexOf(first.first[i])
            secondScore = types.indexOf(second.first[i])

            if (firstScore != secondScore) return@Comparator firstScore - secondScore
        }
        throw Exception("fuck")
    }
    data.sortWith(handComparator)

    for (i in data.indices) count += data[i].second * (i + 1)
    println(count)
}

fun calcHandScore(hand: String): Int { // A score based on the hand (eg. 0 for high card, 1 for one pair) (NOT RANK)
    val occurrences: MutableMap<Char, Int> = types.associateWith { 0 }.toMutableMap()
    for (char in hand.toCharArray()) occurrences[char] = occurrences[char]!!.plus(1)

    val pairs = occurrences.values.count { it == 2 }
    if (pairs == 2) return 2 // Two pair

    for (amount in occurrences.values) {
        when (amount) {
            5 -> return 6 // Five of a kind
            4 -> return 5 // Four of a kind
            3 -> return if (pairs == 1) 4 else 3 // Full house or three of a kind
        }
    }
    return if (pairs == 1) 1 else 0 // One pair or high card
}

fun calcPartTwoScore(hand: String): Int { // Joker cards as wildcards
    var highest = -1
    for (char in types) {
        val newScore = calcHandScore(hand.replace('J', char))
        if (newScore > highest) highest = newScore
    }
    return highest
}
