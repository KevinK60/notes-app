

package models

import java.util.*

data class Note(var noteTitle: String, var notePriority: Int, var noteCategory: String, var isNoteArchived :Boolean, var NoteDate: Date = Date(), var duedate: Date = Date()) {
    override fun toString(): String =  """
        
        
             ------------Note--------------------
            〣     "Title: $noteTitle               
            〣   "Priority: $notePriority,              
            〣    "Category: $noteCategory,           
            〣   "Archived: $isNoteArchived       
            〣   "Created: $NoteDate
            〣   "Due: $duedate
            ☴☴☴☴☴☴☴☴☴☴☴☴☴☴☴☴☴☴☴☴☴☴☴☴☴       
             
             """


}