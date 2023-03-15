
import controllers.NoteAPI
import models.Note
import mu.KotlinLogging
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.lang.System.exit
import java.util.*

val scanner = Scanner(System.`in`)
private val logger = KotlinLogging.logger {}
private val noteAPI = NoteAPI()

fun main(args: Array<String>) {
    println("Hello World!")



    runMenu()
}
fun mainMenu() : Int {
    return readNextInt(""" 
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

    val noteTitle = readNextLine("Enter a title for the note: ")
    val notePriority = readNextInt("Enter a priority (1-low, 2, 3, 4, 5-high): ")
    val noteCategory = readNextLine("Enter a category for the note: ")
    val isAdded = noteAPI.add(Note(noteTitle, notePriority, noteCategory, false))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}


fun listNotes(){
    println(noteAPI.listAllNotes())

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
