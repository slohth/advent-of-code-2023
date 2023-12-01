fun main() {
    val list: List<String> =  File("src/main/resources/dayone.txt").useLines { it.toList() }

    val digits: Map<String, Int> = mapOf(
        "one" to 1, "two" to 2, "three" to 3, "four" to 4, "five" to 5,
        "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9, "zero" to 0
    )

    // Same as previous map but with reversed keys
    // eg. "owt" -> 2, "enin" -> 9 etc.
    val reversedDigits: MutableMap<String, Int> = mutableMapOf()
    digits.forEach { entry -> reversedDigits[entry.key.reversed()] = entry.value }

    var count = 0
    for (str in list) {
        val firstDigit = getInitialDigitFromString(str, digits) // Search left -> right for first digit
        val lastDigit = getInitialDigitFromString(str.reversed(), reversedDigits) // Search right -> left for last digit

        val total = "$firstDigit$lastDigit".toInt() // Concatenate the digits and add to total
        count += total
    }

    println(count)
}

fun getInitialDigitFromString(string: String, values: Map<String, Int>): Int {
    for (i in string.indices) { //  0 to length - 1

        val parsed = string.subSequence(0, i) // The portion of the string we're up to
        for (key in values.keys) if (parsed.contains(key)) return values[key]!! // If this contains a string digit, return the digit

        if (string[i].isDigit()) return string[i].digitToInt() // If the character is a digit, return the digit

    }
    return -1
}
