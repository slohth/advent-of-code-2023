val list: List<String> =  File("src/main/resources/dayninetest.txt").useLines { it.toList() }
var count = 0L

fun main() {
    val data: MutableList<LongArray> = mutableListOf() // A list of sequences to test

    for (line in list) { // Format data
        data.add(line.split(" ").mapNotNull { it.toLongOrNull() }.toLongArray())
    }

    for (i in data.indices) {
        print("\n")
        var sequence = data[i]
        printArray(sequence)

        val lastNumbers: MutableList<Long> = mutableListOf() // The last numbers of each sequence step
        val firstNumbers: MutableList<Long> = mutableListOf() // the first nums
        do {
            lastNumbers.add(sequence.last())
            firstNumbers.add(sequence.first())
            sequence = getDifferences(sequence)
        } while (!isLastArray(sequence))

        lastNumbers.reverse() // Top-down -> Bottom-up
        firstNumbers.reverse()

        val extrapolatedFutureNumbers = LongArray(lastNumbers.size)
        for (x in lastNumbers.indices) {
            val sublist = lastNumbers.subList(0, x + 1)
            extrapolatedFutureNumbers[x] = sublist.sum()
        }

        val extrapolatedPreviousNumbers = LongArray(firstNumbers.size)
        for (x in firstNumbers.indices) {
            if (x == 0) { extrapolatedPreviousNumbers[0] = firstNumbers[x]; continue }
            extrapolatedPreviousNumbers[x] = firstNumbers[x] - extrapolatedPreviousNumbers[x - 1]
        }

        println("Next number in sequence = ${extrapolatedFutureNumbers.last()}") // Part 1
        println("Previous number in sequence = ${extrapolatedPreviousNumbers.last()}") // Part 2

        count += extrapolatedPreviousNumbers.last()
    }

    println(" ")
    println("Sum of extrapolated nums: $count")

}

fun getDifferences(nums: LongArray): LongArray {
    val array = LongArray(nums.size - 1)
    for (i in nums.indices) {
        if (i == nums.size - 1) continue
        array[i] = nums[i + 1] - nums[i] // diff of nums
    }
    return array
}

fun isLastArray(nums: LongArray): Boolean {
    for (num in nums) if (num != 0L) return false
    return true
}

fun printArray(nums: LongArray) {
    for (num in nums) print("$num ")
    print("\n")
}
