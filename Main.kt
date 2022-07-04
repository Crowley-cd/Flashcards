package flashcards

import java.io.File

val time = "${java.time.LocalDateTime.now()})"
var logFileName = "temp.log"
val fileLog = File(logFileName)
var STATUS_OF_PROGRAMM = true
val cards = Cards()


fun main(args: Array<String>) {
    var importFileArg: String
    var exportFileArg: String
    if (args.contains("-import") || args.contains("-export")) {
        for (numArg in 0..args.lastIndex) {
            if (args[numArg] == "-import") {
                importFileArg = args[numArg + 1]
                cards.importArg(importFileArg)
            }
            if (args[numArg] == "-export") {
                exportFileArg = args[numArg + 1]
                cards.exportArg = exportFileArg
            }
        }
    }



    fileLog.appendText("\n $time")
    while (STATUS_OF_PROGRAMM == true) {
        mainMenu(cards)

    }
}

fun mainMenu(cards: Cards) {
    println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):")
    var select = readln()
    when (select) {
        "add" -> cards.addCardFromText()
        "remove" -> cards.remove()
        "import" -> cards.addCardsFromFile()
        "export" -> cards.export()
        "ask" -> cards.Check()
        "exit" -> exit()
        "log" -> cards.saveLog()
        "reset stats" -> {
            for (i in cards.cards.indices){
                cards.cards[i].errors = 0
                cards.tempHardList.clear()
            }
            println("Card statistics have been reset.")
        }
        "hardest card" -> cards.checkHardestCard()
        else -> {
            println("Try again")
            mainMenu(cards)
        }
    }
}


fun exit() {
    println("Bye bye!")
    cards.exportArg(cards.exportArg)
    STATUS_OF_PROGRAMM = false
}

fun println(str: String) {
    kotlin.io.println(str)
    fileLog.appendText("\n${str}")
}

fun readln():String {

    val out =  kotlin.io.readln()
    fileLog.appendText("\n$out")
    return out
}
