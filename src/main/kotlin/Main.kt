

import controllers.NoteAPI
import models.Note
import mu.KotlinLogging
import persistence.JSONSerializer
import persistence.XMLSerializer
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import java.lang.System.exit
import java.util.*


val scanner = Scanner(System.`in`)
private val logger = KotlinLogging.logger {}
private val noteAPI = NoteAPI(XMLSerializer(File("notes.xml")))
//private val noteAPI = NoteAPI(JSONSerializer(File("notes.json")))
//rivate val noteAPI = NoteAPI(YAMLSerializer(File("notes.yaml")))
fun main(args: Array<String>) {
    println("Hello World!")



    runMenu()
}
fun mainMenu() : Int {
    return readNextInt(""" 
         > ┃﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏ ┃
         > ┃        NOTE KEEPER APP         ┃
         > ┃﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏ ┃
         > ┃ NOTE MENU                      ┃
         > ┃  1) Add a note                 ┃
         > ┃   2) List all notes            ┃
         > ┃   3) Update a note             ┃
         > ┃  4) Delete a note              ┃
         > ┃   5) Search notes              ┃
         > ┃   6) List notes by category    ┃   
         > ┃   7) Archive a note            ┃ 
         >     8) Sort by created date      ┃                                                                                                                                        
         > ┃   20) Save                     ┃
         > ┃   21) Load                     ┃
         > ┃﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏ ┃
         > ┃   0) Exit                      ┃
         > ┃﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏ ┃
         > ==>> """.trimMargin(">"))
}
fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1  -> addNote()
            2  -> listnotes()
            3  -> updateNote()
            4  -> deleteNote()
            5  -> searchNotes()
            6  -> listNotesBySelectedCategory()
            7  -> archiveNote()
            8  -> sortByNoteDate()
            0  -> exitApp()
            20 -> save()
            21 -> load()
            else -> println("Invalid option entered: " + option)
        }
    } while (true)
}

fun sortByNoteDate() {
    print(noteAPI.sortbydates())
}

fun searchNotes() {
    val searchTitle = readNextLine("Enter the description to search by: ")
    val searchResults = noteAPI.searchByTitle(searchTitle)
    if (searchResults.isEmpty()) {
        println("No notes found")
    } else {
        println(searchResults)
    }
}

fun addNote(){

    val noteTitle = readNextLine("Enter a title for the note: ")
    val notePriority = readNextInt("Enter a priority (1-low, 2, 3, 4, 5-high): ")
    val noteCategory = readNextLine("Enter a category for the note: ")
    var duedate: Date = Date()
    val addDate = readNextLine("Would you like add a custom add date to the note? (yes/no): ")
    if (addDate.equals("yes")) {

        val year = readNextInt("Please enter a year for the note: ")
        val month = readNextInt("Please enter a month for the note: ")
        val day = readNextInt("Please enter a day for the note: ")
        duedate = Date(year, month, day)

    }

    val isAdded = noteAPI.add(Note(noteTitle, notePriority, noteCategory, false, duedate))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun listnotes(){
    if (noteAPI.numberOfNotes() > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                  > |   1) View ALL notes          |
                  > |   2) View ACTIVE notes       |
                  > |   3) View ARCHIVED notes     |
                  > --------------------------------
         > ==>> """.trimMargin(">"))

        when (option) {
            1 -> listNotes();
            2 -> listActiveNotes();
            3 -> listArchivedNotes();
            4 -> listNotesBySelectedCategory()
            else -> println("Invalid option entered: " + option);
        }
    } else {
        println("Option Invalid - No notes stored");
    }
}

fun listNotesBySelectedCategory() {
    val category = readNextLine("Enter a category to filter by: ")
    println(noteAPI.listNotesBySelectedCategory(category))
}


fun listActiveNotes() {
    println(noteAPI.listActiveNotes())
}

fun listArchivedNotes() {
    println(noteAPI.listArchivedNotes())
}

fun listNotes(){
    println(noteAPI.listAllNotes())

}

fun updateNote() {
    //logger.info { "updateNotes() function invoked" }
    listNotes()
    if (noteAPI.numberOfNotes() > 0) {
        //only ask the user to choose the note if notes exist
        val indexToUpdate = readNextInt("Enter the index of the note to update: ")
        if (noteAPI.isValidIndex(indexToUpdate)) {
            val noteTitle = readNextLine("Enter a title for the note: ")
            val notePriority = readNextInt("Enter a priority (1-low, 2, 3, 4, 5-high): ")
            val noteCategory = readNextLine("Enter a category for the note: ")

            //pass the index of the note and the new note details to NoteAPI for updating and check for success.
            if (noteAPI.updateNote(indexToUpdate, Note(noteTitle, notePriority, noteCategory, false))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no notes for this index number")
        }
    }
}

fun deleteNote(){
    //logger.info { "deleteNotes() function invoked" }
    listNotes()
    if (noteAPI.numberOfNotes() > 0) {
        //only ask the user to choose the note to delete if notes exist
        val indexToDelete = readNextInt("Enter the index of the note to delete: ")
        //pass the index of the note to NoteAPI for deleting and check for success.
        val noteToDelete = noteAPI.deleteNote(indexToDelete)
        if (noteToDelete != null) {
            println("Delete Successful! Deleted note: ${noteToDelete.noteTitle}")
        } else {
            println("Delete NOT Successful")
        }
    }
}
fun archiveNote() {
    listActiveNotes()
    if (noteAPI.numberOfActiveNotes() > 0) {
        // only ask the user to choose the note to archive if active notes exist
        val indexToArchive = readNextInt("Enter the index of the note to archive: ")
        // pass the index of the note to NoteAPI for archiving and check for success.
        if (noteAPI.archiveNote(indexToArchive)) {
            println("Archive Successful!")
        } else {
            println("Archive NOT Successful")
        }
    }
}

fun save() {
    try {
        noteAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load() {
    try {
        noteAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

fun exitApp(){

    logger.info { "exitApp() function invoked" }
    exit(0)

}
