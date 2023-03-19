package controllers

import models.Note
import persistence.Serializer
import java.util.stream.Stream


class NoteAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType

    private var notes = ArrayList<Note>()

    fun add(note: Note): Boolean {
        return notes.add(note)
    }

    fun listAllNotes(): String =
        if (notes.isEmpty()) "No notes stored"
        else notes.joinToString(separator = "\n") { note ->
            notes.indexOf(note).toString() + ": " + note.toString()
        }


    fun numberOfNotes(): Int {
        return notes.size
    }

    fun findNote(index: Int): Note? {
        return if (isValidListIndex(index, notes)) {
            notes[index]
        } else null
    }

    //utility method to determine if an index is valid in a list.
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    fun numberOfActiveNotes(): Int {
        return notes.stream()
            .filter { note: Note -> !note.isNoteArchived }
            .count()
            .toInt()
    }

    fun archiveNote(indexToArchive: Int): Boolean {
        if (isValidIndex(indexToArchive)) {
            val noteToArchive = notes[indexToArchive]
            if (!noteToArchive.isNoteArchived) {
                noteToArchive.isNoteArchived = true
                return true
            }
        }
        return false
    }


    fun numberOfArchivedNotes(): Int {
        return notes.stream()
            .filter { note: Note -> note.isNoteArchived }
            .count()
            .toInt()
    }

    fun listArchivedNotes(): String =
        if (numberOfArchivedNotes() == 0 || notes.isEmpty()) "No archived notes stored"
        else (notes.filter { note -> note.isNoteArchived }.toString())


    fun listActiveNotes(): String =
        if (numberOfActiveNotes() == 0 || notes.isEmpty()) "No active notes stored"
        else (notes.filter { note -> !note.isNoteArchived }.toString())


    fun numberOfNotesByPriority(priority: Int): Int = notes.count {
        it.notePriority == priority
    }


    fun listNotesBySelectedPriority(priority: Int): String =
        if (notes.isEmpty()) "No notes stored"
        else {
            val listOfNotes = (notes.filter { note -> note.notePriority == priority })
            if (listOfNotes.equals("")) "No notes $priority"
            else "${numberOfNotesByPriority(priority)} notes with priority $priority: $listOfNotes"
        }


    fun listNotesBySelectedCategory(category: String): String =
        if (notes.isEmpty()) "No notes stored"
        else {
            val listOfNotes = (notes.filter { note -> note.noteCategory == category })
            if (listOfNotes.equals("")) "No notes with category: $category"
            else "${numberOfNotesByCategory(category)} notes with category $category: $listOfNotes"
        }



        fun numberOfNotesByCategory(category: String): Int {
            return notes.stream()
                .filter { note: Note -> note.noteCategory == category }
                .count()
                .toInt()
        }


        fun deleteNote(indexToDelete: Int): Note? {
            return if (isValidListIndex(indexToDelete, notes)) {
                notes.removeAt(indexToDelete)
            } else null
        }

        @Throws(Exception::class)
        fun load() {
            notes = serializer.read() as ArrayList<Note>
        }

        @Throws(Exception::class)
        fun store() {
            serializer.write(notes)
        }

        fun updateNote(indexToUpdate: Int, note: Note?): Boolean {
            //find the note object by the index number
            val foundNote = findNote(indexToUpdate)

            //if the note exists, use the note details passed as parameters to update the found note in the ArrayList.
            if ((foundNote != null) && (note != null)) {
                foundNote.noteTitle = note.noteTitle
                foundNote.notePriority = note.notePriority
                foundNote.noteCategory = note.noteCategory
                return true
            }

            //if the note was not found, return false, indicating that the update was not successful
            return false
        }

        fun isValidIndex(Index: Int): Boolean {
            return isValidListIndex(Index, notes);
        }


    }









