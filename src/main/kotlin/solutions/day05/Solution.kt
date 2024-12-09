package solutions.day05

import solutions.GenericSolution
import util.readLines
import java.io.File

/*
Part 1:
In each update, we look at each page.
We get the rules that say the page is before another page and compare the pages before it with the rule.
If we find an element before it that rules say should be after, we know that the update breaks rules,
and we mark it as such.
The result is the sum of the middle elements of the updates that didn't break any rules.

Part 2:
We use the algorithm from part 1 to determine which updates break rules.
We then sort these using a comparator that takes into account the ordering specified by the rules.
The result is the sum of the middle elements of the fixed updates.

We could rewrite both parts to sort all updates based on the rules as we do in part 2,
and then determine the results by comparing the original updates with the sorted updates.
 */
class Solution : GenericSolution {
    private var rules = mutableListOf<Pair<Int, Int>>()

    private fun readInput(inputFile: File): List<List<Int>> {
        val lines = readLines(inputFile)
        val updates = mutableListOf<List<Int>>()
        var readingRules = true
        for (line in lines) {
            if (line.isBlank()) {
                readingRules = false
                continue
            }
            if (readingRules) {
                val numbers = line.split('|').map { x -> x.toInt() }
                rules.add(Pair(numbers[0], numbers[1]))
            }
            if (!readingRules) {
                val numbers = line.split(',').map { x -> x.toInt() }
                updates.add(numbers)
            }
        }
        return updates
    }

    override fun runPart1(inputFile: File): String {
        val updates = readInput(inputFile)
        val orderedUpdates = updates.filter { update -> isUpdateOrdered(update) }
        return orderedUpdates.sumOf { update -> update[update.size / 2] }.toString()
    }

    private fun isUpdateOrdered(update: List<Int>): Boolean {
        for (pageIndex in update.indices) {
            val anyRuleBroken = rules.filter { r -> r.first == update[pageIndex] }
                .any { isRuleBroken(it, pageIndex, update) }
            if (anyRuleBroken) {
                return false
            }
        }
        return true
    }

    private fun isRuleBroken(rule: Pair<Int, Int>, pageIndex: Int, update: List<Int>): Boolean {
        for (pageIndexBefore in 0 until pageIndex) {
            if (update[pageIndexBefore] == rule.second) {
                return true
            }
        }
        return false
    }

    override fun runPart2(inputFile: File): String {
        val updates = readInput(inputFile)
        val fixedUpdates = updates.filter { update -> !isUpdateOrdered(update) }
            .map { update -> update.sortedWith(ruleComparator) }
        return fixedUpdates.sumOf { update -> update[update.size / 2] }.toString()
    }

    private val ruleComparator = object : Comparator<Int> {
        override fun compare(a: Int, b: Int): Int {
            if (rules.any { r -> r.first == a && r.second == b }) {
                return -1
            }
            if (rules.any { r -> r.first == b && r.second == a }) {
                return 1
            }
            return 0
        }
    }
}
