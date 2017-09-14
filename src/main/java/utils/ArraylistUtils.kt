package utils
/*
* this is a reduction of binary search
 * we will find an element or a place to insert the element and return the closest element
* */
fun <E : Comparable<E>> ArrayList<E>.binarySearchFindClosestInsertElement(input: E): String {
    // find an index where the arr[index] is bigger then input and arr[index-1] is smaller
    // if arr[index] is equal to input -> return i
    var rangeStart = 0
    var rangeEnd = this.size
    while (rangeStart < rangeEnd) {
        val midIndex = rangeStart + (rangeEnd - rangeStart) / 2
        if (this[midIndex] == input) {// found the same word
            return this[midIndex].toString()
        } else if (this[midIndex] > input && midIndex == 0){
            val s = this[midIndex].toString()
            this.add(midIndex, input)
            return s
        }  else if  (this[midIndex] < input && midIndex == this.size - 1) {// the value is
            val s = this[midIndex].toString()
            this.add(input)
            return s
        } else if (this[midIndex] > input && this[midIndex - 1] < input) {// found a word that is bigger and a word that is smaller
            return if (input.compareTo(this[midIndex]) < input.compareTo(this[midIndex - 1])) {
                val s = this[midIndex].toString()
                this.add(midIndex, input)
                s
            } else {
                val s = this[midIndex - 1].toString()
                this.add(midIndex, input)
                s
            }
        } else if (this[midIndex] < input) {// value not found - continue for another iteration
            rangeStart = midIndex + 1
        } else {
            rangeEnd = midIndex
        }
    }
    return ""

}