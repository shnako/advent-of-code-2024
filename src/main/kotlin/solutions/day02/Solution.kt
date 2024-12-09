package solutions.day02

import solutions.GenericSolution
import util.readLines
import java.io.File
import kotlin.math.abs

/*
We read the input and split each line to extract the levels.

Part 1:
For each report, we go through each pair of adjacent levels and compare them according to the rules.
If all pairs match all the rules, then the report is safe.
The result is the count of all safe reports.

Part 2:
Same as part 1, but if a non-safe pair is found, we try removing each level and then rechecking without it.
If any of the reports with a removed level is safe, then we consider the whole report safe.

There are more efficient ways of doing this (e.g. by checking against the next element once an unsafe one is found),
but these are not necessary to get the correct result.
 */
class Solution : GenericSolution {
    private fun readInput(inputFile: File): List<List<Int>> {
        return readLines(inputFile)
            .map { line -> line.split("\\s+".toRegex()).map(String::toInt) }
    }

    override fun runPart1(inputFile: File): String {
        val reports = readInput(inputFile)
        return reports
            .map { report -> isSafe(report, false) }
            .count { safe -> safe }
            .toString()
    }

    override fun runPart2(inputFile: File): String {
        val reports = readInput(inputFile)
        return reports
            .map { report -> isSafe(report, true) }
            .count { safe -> safe }
            .toString()
    }

    private fun isSafe(report: List<Int>, checkWithDampener: Boolean): Boolean {
        val isIncreasing = report[0] < report[1]
        for (i in 1 until report.size) {
            if ((report[i - 1] == report[i])
                || (isIncreasing && report[i - 1] > report[i])
                || (!isIncreasing && report[i - 1] < report[i])
                || (abs(report[i - 1] - report[i]) > 3)
            ) {
                return if (checkWithDampener) checkIsSafeWithoutLevel(report) else false
            }
        }
        return true
    }

    private fun <T> Iterable<T>.withoutItemAt(index: Int): List<T> =
        filterIndexed { i, _ -> i != index }

    private fun checkIsSafeWithoutLevel(report: List<Int>): Boolean {
        return report.indices
            .any { i -> isSafe(report.withoutItemAt(i), false) }
    }
}
