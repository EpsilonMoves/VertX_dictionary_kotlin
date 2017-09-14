package dictionary

import io.vertx.core.json.JsonObject


interface DictionaryInterface {
    fun dictionaryClosest(word:String): JsonObject
}