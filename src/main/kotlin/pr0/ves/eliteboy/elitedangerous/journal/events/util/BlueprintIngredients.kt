package pr0.ves.eliteboy.elitedangerous.journal.events.util

import java.io.Serializable
import javax.persistence.*


@Entity
class BlueprintIngredients : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    @OneToMany(cascade = arrayOf(CascadeType.ALL), targetEntity = Ingredient::class)
    var ingredients: MutableSet<Ingredient>? = null
}
