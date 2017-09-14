package dictionary

import io.vertx.core.json.JsonObject
import utils.JSON_KEY_LEXICAL
import utils.JSON_KEY_VALUE
import utils.binarySearchFindClosestInsertElement

/*
* a class that handles the dictionary
* */
class Dictionary : DictionaryInterface {
    // insert the word to the dictionary and return the closest word by value and closest word by lexical order
    override fun dictionaryClosest(word: String): JsonObject {
        val json = JsonObject()
        json.put(JSON_KEY_VALUE, insertToDictionaryReturnClosest(valueArrayList, StringValuePair(word)))
        json.put(JSON_KEY_LEXICAL, insertToDictionaryReturnClosest(lexicalArrayList, word))
        return json
    }

    // insert word input to the dictionary array list
    private fun <E : Comparable<E>> insertToDictionaryReturnClosest(arr: ArrayList<E>, input: E): String? {
        return when (arr.size) {
            0 -> {
                arr.add(input)
                null
            }
            else -> {
                arr.binarySearchFindClosestInsertElement(input)
            }
        }
    }

    // the dictionary itself
    companion object {
        val valueArrayList = ArrayList<StringValuePair>()
        val lexicalArrayList = ArrayList<String>()
    }
}


