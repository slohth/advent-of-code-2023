val list: List<String> =  File("src/main/resources/daysix.txt").useLines { it.toList() }

fun main() {
    val time = list[0].split(":")[1].split("\\s+".toRegex()).joinToString("").toLong()
    val record = list[1].split(":")[1].split("\\s+".toRegex()).joinToString("").toLong()

    println(potentialNewRecord(time, record)) // For part 1, run this method on each pair of numbers
}

fun potentialNewRecord(time: Long, record: Long): Int {
    var beaten = 0
    for (i in 0..time) if ((time - i) * i > record) beaten++
    return beaten
}
