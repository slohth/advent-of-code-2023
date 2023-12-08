val list: List<String> =  File("src/main/resources/dayeight.txt").useLines { it.toList() }
var count = 0L

val data: MutableMap<String, Pair<String, String>> = mutableMapOf()

fun main() {
    val startingNodes: MutableList<String> = mutableListOf() // A list of the nodes we are starting with
    val amounts: MutableList<Long> = mutableListOf() // A list of amounts of steps it took to reach a destination node
    val map = list[0] // The directional map, eg. "LRR"

    for (i in 2 until list.size) { // Format data
        val regex = Regex("[A-Z0-9]+").findAll(list[i]).map { it.value }.toList()
        if (regex[0].endsWith('A')) startingNodes.add(regex[0])
        data[regex[0]] = regex[1] to regex[2]
    }

    var finished = false
    do {
        for (char in map) {

            val toRemove: MutableList<String> = mutableListOf() // We need a toRemove list to prevent index complications
            finished = true

            for (i in startingNodes.indices) {
                val destination = getDestination(startingNodes[i], char == 'L')
                startingNodes[i] = destination // Replace node to check

                if (destination.endsWith('Z')) {
                    toRemove.add(destination) // Remove this node and add amount of steps taken
                    amounts.add(count + 1)
                    continue
                }

                finished = false
            }
            startingNodes.removeAll(toRemove)

            count++
        }
    } while (!finished)

    println(lcm(amounts)) // Lowest common multiple steps of all required
}

fun getDestination(starting: String, isLeft: Boolean): String {
    val pair = data[starting]!!
    return if (isLeft) pair.first else pair.second
}

fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

fun lcm(a: Long, b: Long): Long = if (a == 0L || b == 0L) 0 else (a * b) / gcd(a, b)

fun lcm(numbers: List<Long>): Long = numbers.reduce { lcm, nextNumber -> lcm(lcm, nextNumber) }
