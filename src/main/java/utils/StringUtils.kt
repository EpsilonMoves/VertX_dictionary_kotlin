package utils

// extension function - if the input consist of letters and spaces return true, invalid chars are symbols and numbers
fun String.assertInput(): Boolean {
    for (c in this.removeSpaces()){
        if (!c.isLetter()){return false}
    }
    return true
}

// extension function - removes spaces
fun String.removeSpaces(): String = this.replace(" ", "")// remove spaces

// extension function - return the sum of the string after converted e.g a=1 b=2
// if the input is ab the returned value will be 3
fun String.extractValueFromString(): Int = this.removeSpaces().toCharArray().sumBy { it.toUpperCase().toInt() - UPPER_CASE_TO_NUMBER_GAP }
