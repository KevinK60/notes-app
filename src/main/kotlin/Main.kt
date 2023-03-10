package utils
import mu.KotlinLogging
import java.lang.System.exit
import java.util.*

val scanner = Scanner(System.`in`)
private val logger = KotlinLogging.logger {}

fun main(args: Array<String>) {
    println("Hello World!")



    runMenu()
}
fun mainMenu() : Int {
    return ScannerInput.readNextInt(""" 
         > ----------------------------------
         > |        NOTE KEEPER APP         |
         > ----------------------------------
         > | NOTE MENU                      |
         > |   1) Add a note                |
         > |   2) List all notes            |
         > |   3) Update a note             |
         > |   4) Delete a note             |
         > ----------------------------------
         > |   0) Exit                      |
         > ----------------------------------
         > ==>> """.trimMargin(">"))
}
fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1  -> addNote()
            2  -> listNotes()
            3  -> updateNote()
            4  -> deleteNote()
            0  -> exitApp()
            else -> println("Invalid option entered: " + option)
        }
    } while (true)
}
fun addNote(){
    println("You chose Add Note")
    logger.info { "addNote() function invoked" }
}

fun listNotes(){
    println("You chose List Notes")
    logger.info { "addNote() function invoked" }

}

fun updateNote(){
    println("You chose Update Note")
    logger.info { "updateNote() function invoked" }
}

fun deleteNote(){
    println("You chose Delete Note")
    logger.info { "delete() function invoked" }
}

fun exitApp(){
    println("Exiting...bye")
    logger.info { "exitApp() function invoked" }
    exit(0)

}
