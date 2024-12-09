package solutions.day01

import solutions.GenericSolution
import util.readLines
import java.io.File
import kotlin.math.abs

/*
We read the input and split each line to extract the 2 lists of locations.

Part 1:
We sort the 2 lists and then sum together the absolute of the 2 values at each index to get the result.

Part 2:
We multiply each location in the first list with the number of occurrences in the second.
The result is the sum of these.
We could optimise this by storing the calculated similarity scores
and looking them up rather than calculating them on the second occurrence,
but it's not necessary for this input.
 */
class Solution : GenericSolution {
    private fun readInput(inputFile: File): List<Pair<Int, Int>> {
        return readLines(inputFile)
            .map { line ->
                val (a, b) = line.split("\\s+".toRegex()).map(String::toInt)
                a to b
            }
    }

    override fun runPart1(inputFile: File): String {
        val locationPairs = readInput(inputFile)
        val locations1 = locationPairs.map { l -> l.first }.sorted()
        val locations2 = locationPairs.map { l -> l.second }.sorted()

        var result = 0
        for (i in locations1.indices) {
            result += abs(locations1[i] - locations2[i])
        }

        return result.toString()
    }

    override fun runPart2(inputFile: File): String {
        val locationPairs = readInput(inputFile)
        val locations2 = locationPairs.map { l -> l.second }

        val result = locationPairs
            .sumOf { l -> l.first * locations2.count { l2 -> l2 == l.first } }

        return result.toString()
    }
}
