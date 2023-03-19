

package models

data class Note(var noteTitle: String, var notePriority: Int, var noteCategory: String, var isNoteArchived :Boolean){
    override fun toString(): String =  """
        
        
             ------------Note--------------------
            〣     "Title: $noteTitle               
            〣   "Priority: $notePriority,              
            〣    "Category: $noteCategory,           
            〣   "Archived: $isNoteArchived         
            ☴☴☴☴☴☴☴☴☴☴☴☴☴☴☴☴☴☴☴☴☴☴☴☴☴       
             
             """


}