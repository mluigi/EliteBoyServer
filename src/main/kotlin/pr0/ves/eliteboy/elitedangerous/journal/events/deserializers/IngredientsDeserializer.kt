package pr0.ves.eliteboy.elitedangerous.journal.events.deserializers

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import pr0.ves.eliteboy.elitedangerous.journal.events.util.BlueprintIngredients
import pr0.ves.eliteboy.elitedangerous.journal.events.util.Ingredient
import java.lang.reflect.Type

class IngredientsDeserializer : JsonDeserializer<BlueprintIngredients> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): BlueprintIngredients {
        val ing = BlueprintIngredients()
        if (json!!.isJsonArray) {
            val type = object : TypeToken<MutableSet<Ingredient>>() {}.type
            ing.ingredients = Gson().fromJson(json, type)
        } else if (json.isJsonObject) {
            val type = object : TypeToken<Map<String, Int>>() {}.type
            val map = Gson().fromJson<Map<String, Int>>(json, type)
            ing.ingredients = mutableSetOf()
            map.forEach { t, u ->
                ing.ingredients!!.add(Ingredient().also {
                    it.Name = t
                    it.Count = u
                })
            }
        }

        return ing
    }

}