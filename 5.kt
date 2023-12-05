val list: List<String> =  File("src/main/resources/dayfive.txt").useLines { it.toList() }
var lowest = Long.MAX_VALUE

fun main() {
    val data: List<MutableList<LongArray>> = listOf( // Tranlsation maps
        mutableListOf(), // Seed to soil
        mutableListOf(), // Soil to fertilizer
        mutableListOf(), // Fertilizer to water
        mutableListOf(), // Water to light
        mutableListOf(), // Light to temperature
        mutableListOf(), // Temperature to humidity
        mutableListOf() // Humidity to location
    )

    var dataIndex = -1
    for (line in list) { // Formatting data
        if (line.isBlank() || line.contains("seeds")) continue // Ignore blank lines
        if (line.contains("map")) { dataIndex++; continue } // If new map data, append index

        data[dataIndex].add(
            line.split(" ").mapNotNull { it.toLongOrNull() }.toLongArray()
        )
    }

    // Calculating seeds
    val nums = list[0].split(":")[1].split(" ").mapNotNull { it.toLongOrNull() }
    // For part one, the whole seeds list is literally this "nums" value ^

    val checkedRanges: MutableList<Pair<Long, Long>> = mutableListOf() // Cache checked ranges to avoid repeats
    for (i in nums.indices) {
        if (i % 2 != 0) continue // We're only interested in the first number of each pair

        val rangeStart = nums[i]
        val rangeEnd = (nums[i] + nums[i + 1]) - 1

        for (seed in rangeStart..rangeEnd) { // Calculate data

            var skip = false
            for (range in checkedRanges) { // If we've already checked this seed, skip
                if (seed >= range.first && seed <= range.second) skip = true
            }
            if (skip) continue

            val soil = getDestinationNumber(seed, data[0])
            val fertilizer = getDestinationNumber(soil, data[1])
            val water = getDestinationNumber(fertilizer, data[2])
            val light = getDestinationNumber(water, data[3])
            val temp = getDestinationNumber(light, data[4])
            val humid = getDestinationNumber(temp, data[5])
            val location = getDestinationNumber(humid, data[6])

            if (location < lowest) lowest = location
        }

        checkedRanges.add(rangeStart to rangeEnd) // Add this range to already checked list
    }
    println(lowest)
}

fun getDestinationNumber(sourceNumber: Long, map: List<LongArray>): Long { // Return destination number given a source number and map

    for (translation in map) {
        val sourceRange = translation[1] until (translation[1] + translation[2])
        if (!sourceRange.contains(sourceNumber)) continue // If this translation doesn't contain the source number, ignore
        return translation[0] + (sourceNumber - translation[1]) // Return destinationRangeStart + how far we are in this range
    }

    return sourceNumber
}
