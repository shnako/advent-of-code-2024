package solutions.day03

import solutions.GenericSolution
import util.readFileAsString
import java.io.File

/*
In both parts, we make heavy use of regular expressions.

Part 1:
We use regex to extract all mul(x,y) segments.
We then use regex to extract the 2 numbers from each segment.
We multiply these numbers and sum the results from each group to get the result.

Part 2:
Same as part 1, but we also look for don't() and do() segments.
When we find a don't() group, segment we disable the calculation, and enable it when we find a do() group.
 */
class Solution : GenericSolution {
    override fun runPart1(inputFile: File): String {
        val memory = readFileAsString(inputFile)
        val regexMul = Regex("mul\\(\\d+,\\d+\\)")
        val regexNum = Regex("\\d+")
        val result = regexMul.findAll(memory)
            .map { group ->
                regexNum.findAll(group.value)
                    .map { x -> x.value.toInt() }
                    .fold(1) { a, b -> a * b }
            }
            .sum()
        return result.toString()
    }

    override fun runPart2(inputFile: File): String {
        val memory = readFileAsString(inputFile)
        val regexMulDoNotDo = Regex("(mul\\(\\d+,\\d+\\))|(don't\\(\\))|(do\\(\\))")
        val regexNum = Regex("\\d+")
        val groups = regexMulDoNotDo.findAll(memory).toList().map { x -> x.value }
        var isEnabled = true
        var result = 0
        for (group in groups) {
            if (group == "do()") {
                isEnabled = true
            } else if (group == "don't()") {
                isEnabled = false
            } else if (isEnabled) {
                result += regexNum.findAll(group).toList()
                    .map { x -> x.value.toInt() }
                    .fold(1) { a, b -> a * b }
            }
        }
        return result.toString()
    }
}
