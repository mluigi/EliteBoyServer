package pr0.ves.eliteboy.server.listeners

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

interface JournalWatcherListener {
    fun getEntries(entries: ArrayList<JournalEntry>)
}