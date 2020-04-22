@file:Suppress("MemberVisibilityCanBePrivate", "DuplicatedCode")

package phonebook

import java.io.File
import java.nio.file.Files
import kotlin.math.roundToInt
import kotlin.math.sqrt

fun main() {
    val directoryLines = Files.readAllLines(File("D:/code/kotlin/phoneBookResources/directory.txt").toPath())
    val directory = Array(directoryLines.size) {
        PhoneBookEntry(directoryLines[it].split(' ', limit = 2))
    }
    val names = Files.readAllLines(File("D:/code/kotlin/phoneBookResources/find.txt").toPath())

    val millis = linearSearch(directory, names)

    bubbleSortJumpSearch(directory, names, millis * 10)
}

fun linearSearch(directory: Array<PhoneBookEntry>, names: List<String>): Long {
    val totalNames = names.size
    val numbers = Array(totalNames) { "" }
    var foundNames = 0

    val startTime = System.currentTimeMillis()
    for ((index, person) in names.withIndex()) {
        inner@for (entry in directory) {
            if (person == entry.name) {
                numbers[index] = entry.number
                foundNames++
                break@inner
            }
        }
    }
    val endTime = System.currentTimeMillis()
    val duration = MinsSecsMillis(endTime - startTime)
    println("Found $foundNames / $totalNames entries. Time taken: $duration")
    return endTime - startTime
}

fun bubbleSortJumpSearch(directory: Array<PhoneBookEntry>, names: List<String>, maxSortTime: Long) {
    val directorySize = directory.size
    val totalNames = names.size
    val numbers = Array(totalNames) { "" }
    var foundNames = 0

    println("Start searching (bubble sort + jump search)...")
    val sortStartTime = System.currentTimeMillis()
    var unSortedElements = directorySize
    var temp: PhoneBookEntry
    while (unSortedElements > 1) {
        for (i in 0 until unSortedElements - 1) {
            if (directory[i] > directory[i + 1]) {
                temp = directory[i + 1]
                directory[i + 1] = directory[i]
                directory[i] = temp
            }
        }
        unSortedElements--
        if (System.currentTimeMillis() - sortStartTime > maxSortTime) {
            breakBubbleSortToLinearSearch(directory, names, sortStartTime)
            return
        }
    }
    val sortEndTime = System.currentTimeMillis()

    val searchStartTime = System.currentTimeMillis()
    val sqrt = sqrt(directorySize.toDouble()).roundToInt()
    var currentBlockStart: Int
    var currentBlockEnd: Int
    for ((index, person) in names.withIndex()) {
        currentBlockStart = 0
        currentBlockEnd = sqrt

        inner@while (currentBlockStart < directorySize) {
            if (currentBlockEnd > directorySize) {
                currentBlockEnd = directorySize
            }
            // returns positive if the name is greater than the directory name
            if (person <= directory[currentBlockEnd - 1].name) {
                for (innerBlockIndex in currentBlockEnd - 1 downTo currentBlockStart) {
                    if (person == directory[innerBlockIndex].name) {
                        foundNames++
                        numbers[index] = directory[innerBlockIndex].number
                        break@inner
                    }
                    throw Exception("Should be unreachable code; name should have been found in above for loop")
                }
            } else {
                currentBlockStart += sqrt
                currentBlockEnd += sqrt
            }
        }
    }
    val searchEndTime = System.currentTimeMillis()
    val sortDuration = MinsSecsMillis(sortEndTime - sortStartTime)
    val searchDuration = MinsSecsMillis(searchEndTime - searchStartTime)
    val totalDuration = MinsSecsMillis(searchEndTime - sortStartTime)
    println("Found $foundNames out of $totalNames entries. Time taken: $totalDuration")
    println("Sorting time: $sortDuration")
    println("Searching time: $searchDuration")
}

fun breakBubbleSortToLinearSearch(directory: Array<PhoneBookEntry>, names: List<String>, sortStartTime: Long) {
    val sortEndTime = System.currentTimeMillis()
    val totalNames = names.size
    val numbers = Array(totalNames) { "" }
    var foundNames = 0

    println("Start searching (linear search)...")
    val searchStartTime = System.currentTimeMillis()
    for ((index, person) in names.withIndex()) {
        inner@for (entry in directory) {
            if (person == entry.name) {
                numbers[index] = entry.number
                foundNames++
                break@inner
            }
        }
    }
    val searchEndTime = System.currentTimeMillis()
    val sortDuration = MinsSecsMillis(sortEndTime - sortStartTime)
    val searchDuration = MinsSecsMillis(searchEndTime - searchStartTime)
    val totalDuration = MinsSecsMillis(searchEndTime - sortStartTime)
    println("Found $foundNames / $totalNames entries. Time taken: $totalDuration")
    println("Sorting time: $sortDuration - STOPPED, moved to linear search")
    println("Searching time: $searchDuration")
}

class PhoneBookEntry(numberAndName: List<String>): Comparable<PhoneBookEntry> {
    val number = numberAndName[0]
    val name = numberAndName[1]

    /**
     * Compares this object with the specified object for order. Returns zero if this object is equal
     * to the specified [other] object, a negative number if it's less than [other], or a positive number
     * if it's greater than [other].
     */
    override fun compareTo(other: PhoneBookEntry): Int {
        return name.compareTo(other.name)
    }
}

class MinsSecsMillis(durationInMillis: Long) {
    val mins: Long
    val secs: Long
    val millis: Long
    init {
        var input = durationInMillis
        mins = input / 60000
        input -= (mins * 60000)
        secs = input / 1000
        input -= (secs * 1000)
        millis = input
    }

    override fun toString() = "$mins min. $secs sec. $millis ms."
}
