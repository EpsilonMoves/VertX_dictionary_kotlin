package dictionary

import utils.extractValueFromString

/*
* StringValuePair class - contain a word and the sum of it's letters values
* */
data class StringValuePair(private val word: String, private val value: Int):Comparable<StringValuePair> {
    constructor(s: String) : this(s, s.extractValueFromString())
    override fun equals(other: Any?): Boolean = this.value == (other as StringValuePair).value
    override fun toString(): String = word
    override fun compareTo(other: StringValuePair): Int = this.value - other.value
}


