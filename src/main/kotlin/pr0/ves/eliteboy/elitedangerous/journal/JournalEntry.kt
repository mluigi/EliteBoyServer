package pr0.ves.eliteboy.elitedangerous.journal


import com.fasterxml.jackson.annotation.JsonIgnore
import pr0.ves.eliteboy.elitedangerous.companionapi.JournalTypeEnum
import java.time.OffsetDateTime
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(
        uniqueConstraints = arrayOf(UniqueConstraint(columnNames = arrayOf("event", "nLine", "timestamp")))
)
open class JournalEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    @ManyToOne
    @JsonIgnore
    var journal: Journal? = null
    var nLine: Int? = null
    var event: String? = null
    fun eventTypeID() = JournalTypeEnum.valueOf(event!!)
    var timestamp: String? = null
    fun eventTimeUTC() = OffsetDateTime.parse(timestamp)!!

    override fun equals(other: Any?): Boolean {
        var same = false
        if (other != null && other is JournalEntry)
            same = event == other.event &&
                    timestamp == other.timestamp &&
                    nLine == other.nLine
        return same
    }

    override fun hashCode(): Int {
        var result = (nLine ?: 0)
        result = 31 * result + (event?.hashCode() ?: 0)
        result = 31 * result + (timestamp?.hashCode() ?: 0)
        return result
    }
}