package pr0.ves.eliteboy.elitedangerous.journal

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import mu.KLogging
import pr0.ves.eliteboy.elitedangerous.journal.events.LoadGame
import pr0.ves.eliteboy.elitedangerous.journal.events.Scan
import pr0.ves.eliteboy.elitedangerous.journal.events.Unknown
import pr0.ves.eliteboy.elitedangerous.journal.events.deserializers.IngredientsDeserializer
import pr0.ves.eliteboy.elitedangerous.journal.events.deserializers.ScanDeserializer
import pr0.ves.eliteboy.elitedangerous.journal.events.util.BlueprintIngredients
import java.io.File
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.persistence.*


@Entity
class Journal(@Column(unique = true) val filename: String = "") {
    companion object : KLogging() {
        var FDDIR = File(System.getProperty("user.home"), "Saved Games/Frontier Developments/Elite Dangerous")
        fun setFDDIR(folder: String) {
            FDDIR = File(folder)
        }

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null

    @Transient
    val journal = File(FDDIR, if (filename == "") {
        FDDIR.list().last()
    } else {
        filename
    })

    var cmdr: String? = null

    @OneToMany(targetEntity = JournalEntry::class, cascade = arrayOf(CascadeType.ALL), mappedBy = "journal", fetch = FetchType.EAGER)
    var entries: MutableSet<JournalEntry>? = null

    fun dateTime() = OffsetDateTime.parse(
            LocalDateTime.parse(
                    filename.split(".")[1], DateTimeFormatter.ofPattern("yyMMddHHmmss").withZone((ZoneId.of("UTC")))
            ).atOffset(ZoneOffset.UTC).toString()
    )!!

    @Transient
    val gson = GsonBuilder()
            .registerTypeAdapter(Scan::class.java, ScanDeserializer())
            .registerTypeAdapter(BlueprintIngredients::class.java, IngredientsDeserializer())
            .create()!!

    fun readJournal(): ArrayList<JournalEntry> {
        val prev = ArrayList<JournalEntry>().also {
            if (entries != null) {
                it.addAll(entries!!.asIterable())
            }
        }
        val lines = journal.readLines()
        lines.forEach {
            if (entries == null)
                entries = mutableSetOf()

            val entry = getJournalEntryTyped(it).also { entry ->
                if (entry is LoadGame)
                    this.cmdr = entry.Commander!!
                entry.nLine = lines.indexOf(it)
                entry.journal = this
            }
            if (!entries!!.contains(entry)) {
                entries!!.add(entry)
            }

        }

        return ArrayList<JournalEntry>().also {
            it.addAll(entries!!.asIterable())
            it.removeAll(prev)
        }
    }

    fun getJournalEntryTyped(json: String): JournalEntry {
        val jsonObject = JsonParser().parse(json).asJsonObject
        var type = jsonObject.get("event").asString
        type = "pr0.ves.eliteboy.elitedangerous.journal.events." + type
        val className: Class<*>? = try {
            Class.forName(type)
        } catch (e: ClassNotFoundException) {
            null
        }

        return if (className == null) {
            val jEntry = gson.fromJson<Unknown>(jsonObject, Unknown::class.java)
            jsonObject.remove("event")
            jsonObject.remove("timestamp")
            jEntry.also { it.json = jsonObject.toString() }
        } else {
            className.cast(gson.fromJson(jsonObject, className)) as JournalEntry
        }
    }

    override fun equals(other: Any?): Boolean {
        var same = false
        if (other != null && other is Journal) {
            same = journal.path == other.journal.path
        }
        return same
    }

    override fun hashCode(): Int {
        return journal.hashCode()
    }
}