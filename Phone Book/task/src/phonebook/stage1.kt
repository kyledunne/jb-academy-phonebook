//@file:Suppress("MemberVisibilityCanBePrivate")
//
package phonebook
//
//import java.io.File
//import java.nio.file.Files
//
//fun main() {
//    val directoryLines = Files.readAllLines(File("D:/code/kotlin/phoneBookResources/directory.txt").toPath())
//    val directory = Array(directoryLines.size) {
//        PhoneBookEntry(directoryLines[it].split(' ', limit = 2))
//    }
//    val names = Files.readAllLines(File("D:/code/kotlin/phoneBookResources/find.txt").toPath())
//    val totalNames = names.size
//    val numbers = Array(totalNames) { "" }
//    var foundNames = 0
//    println("Start searching...")
//    val startTime = System.currentTimeMillis()
//    for ((index, person) in names.withIndex()) {
//        inner@for (entry in directory) {
//            if (person == entry.name) {
//                numbers[index] = entry.number
//                foundNames++
//                break@inner
//            }
//        }
//    }
//    val endTime = System.currentTimeMillis()
//    val duration = MinsSecsMillis(endTime - startTime)
//    println("Found $foundNames / $totalNames entries. Time taken: $duration")
//}
//
//class PhoneBookEntry(numberAndName: List<String>) {
//    val number = numberAndName[0]
//    val name = numberAndName[1]
//}
//
//class MinsSecsMillis(durationInMillis: Long) {
//    val mins: Long
//    val secs: Long
//    val millis: Long
//    init {
//        var input = durationInMillis
//        mins = input / 60000
//        input -= (mins * 60000)
//        secs = input / 1000
//        input -= (secs * 1000)
//        millis = input
//    }
//
//    override fun toString() = "$mins min. $secs sec. $millis ms."
//}
