package pr0.ves.eliteboy.elitedangerous.journal.events.deserializers

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import pr0.ves.eliteboy.elitedangerous.journal.events.Scan
import pr0.ves.eliteboy.elitedangerous.journal.events.util.AtmoOrMat
import java.lang.reflect.Type

class ScanDeserializer : JsonDeserializer<Scan> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Scan {
        val mats: MutableSet<AtmoOrMat> = mutableSetOf()
        var scan = Scan()
        if (json != null) {
            val matsJson = json.asJsonObject.get("Materials")
            if (matsJson != null) {
                if (matsJson.isJsonArray) {
                    scan = Gson().fromJson(json, Scan::class.java)
                } else if (matsJson.isJsonObject) {
                    val type = object : TypeToken<Map<String, Double>>() {}.type
                    val map: Map<String, Double> = Gson().fromJson(matsJson, type)
                    map.forEach { t, u ->
                        mats.add(AtmoOrMat().also {
                            it.Name = t
                            it.Percent = u
                        })
                    }
                    val jsonObject = json.asJsonObject
                    jsonObject.remove("Materials")
                    scan = Gson().fromJson(jsonObject, Scan::class.java)
                    scan.Materials = mats
                }
            } else {
                scan = Gson().fromJson(json, Scan::class.java)
            }
        }
        return scan
    }


}