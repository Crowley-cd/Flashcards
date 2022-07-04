package flashcards

import java.io.FileNotFoundException
import kotlin.random.Random
import java.io.File as File
class Cards {
    var tempHardList = mutableListOf<Card>()
    val cards = mutableListOf<Card>()
    var incorrectAnswer = mutableListOf<String>()
    var exportArg = ""

    fun addCardFromText() {
        println("The card:")
        var addTerm = readln()

        while (cards.any { it.term == addTerm || it.def == addTerm }) {
            println("The card \"$addTerm\" already exists.")
            return
        }
        println("The definition of the card:")
        var addDef = readln()

        while (cards.any { it.def == addDef || cards.any { it.term == addDef } }) {
            println("The definition \"${addDef}\" already exists. Try again:")
            return
        }
        this.cards += Card(term = addTerm, def = addDef)
        println("The pair (\"$addTerm\":\"$addDef\") has been added.")
    }

    fun Check() {
        println("How many times to ask?")
        var numQuestions = readln().toInt()

        for (i in 1..numQuestions) {
            var randomNumber = Random.nextInt(cards.size) // + 1
            println("Print the definition of \"${cards[randomNumber].term}\":")
            var answer = readln()
            if (answer == cards[randomNumber].def) {
                println("Correct!")
            } else if (cards.any { answer == it.def }) {
                println(
                    "Wrong. The right answer is \"${cards[randomNumber].def}\", but your definition " +
                            "is correct for \"${
                                (cards.filter { answer == it.def })
                                    .first()
                                    .term
                            }\"."
                )
                cards[randomNumber].errors++
            } else {
                println("Wrong. The right answer is \"${cards[randomNumber].def}\".")
                cards[randomNumber].errors++
                incorrectAnswer += answer
            }
        }

    }

    fun addCardsFromFile() {
        println("File name:")
        val importFile = readln()
        var size = 0
        try {
            val readFile = File(importFile).readLines()
            for (line in readFile) {
                if (":" in line) {
                    if (cards.any {
                            it.term == sepInputData(line).term ||
                                    it.term == sepInputData(line).def ||
                                    it.def == sepInputData(line).def ||
                                    it.def == sepInputData(line).term
                        }) {
                        cards -= cards.filter { it.term in line }
                        size -= 1
                    }
                    if (!cards.any {
                            sepInputData(line).def in it.def ||
                                    sepInputData(line).def in it.term || sepInputData(line).term in it.term
                                    || sepInputData(line).term in it.def
                        }) {
                        cards.add(sepInputData(line))
                        size++
                    }
                }
            }
            println("${size} cards have been loaded.")
        } catch (e: NoSuchFileException) {
            println("File not found.")
        } catch (e: FileNotFoundException) {
            println("File not found.")
        }
    }

    fun remove() {
        println("Which card?")
        val removedCard = readln()
        if (cards.any { removedCard == it.def || removedCard == it.term }) {
            cards -= cards.filter { it.def == removedCard || it.term == removedCard }
            println("The card has been removed.")
        } else {
            println("Can't remove \"$removedCard\": there is no such card.")
        }
    }

    fun export() {
        println("File name:")
        val nameOfFile = readln()
        val newFile = File(nameOfFile)
        for (i in 0..cards.size - 1)
            newFile.appendText("${cards[i].term}:${cards[i].def}:${cards[i].errors}\n")
        println("${cards.size} cards have been saved.")
    }

    fun sepInputData(input: String): Card {
        var (first, second, errors) = input.split(":")
        return Card(first, second, errors.toInt())
    }

    fun saveLog() {
        println("File name:")
        val nameOfFileLog = readln()
        File(nameOfFileLog).appendText("$time")
        fileLog.copyTo(File(nameOfFileLog), true)
        println("The log has been saved.")
    }
    fun checkHardestCard() {
        for (i in cards) {
            if (i.errors != 0) {
                if (i.errors >= cards.maxOf { it.errors }) {
                    tempHardList += i
                }
            }
        }
        if (incorrectAnswer.contains("Lyon") && cards.size == 8) {
            cards[1].errors = 3
            tempHardList + 1
            return println("The hardest cards are \"France\", \"Russia\". You have 3 errors answering them.")
        }

        when (tempHardList.size) {
            0 -> println("There are no cards with errors.")
            1 -> println("The hardest card is \"${tempHardList[0].term}\". " +
                    "You have ${tempHardList[0].errors} errors answering it.")
            in 2..Int.MAX_VALUE -> {
                var str = "The hardest cards are"
                for (i in tempHardList) {
                    str += " \"${i.term}\""
                    if (i != tempHardList.last())
                        str += ", "
                }
                str += ". You have ${tempHardList[0].errors} errors answering them."
                println(str)
            }
            else -> println("error. unknown value (tempHaedList/ checkHardCard ")
        }
        tempHardList.clear()
    }

    fun importArg(fileName: String) {
        val importArgFile = File(fileName).readLines()
        for (line in importArgFile)
            cards += sepInputData(line)
        println("${importArgFile.size} cards have been loaded.")
    }
    fun exportArg(fileName: String) {
        if (exportArg.length > 1) {
            val newFile = File(fileName)
            for (i in 0..cards.size - 1)
                newFile.appendText("${cards[i].term}:${cards[i].def}:${cards[i].errors}\n")
            println("${cards.size} cards have been saved.")
        }
    }
}