package aleksandr_logunov.task_list.extensions

fun String.ellipsize(charNum: Int): String {
    var result = this.trim()
    if (result.length > charNum) {
        result = result.substring(0, charNum - 4).padEnd(charNum, '.')
    }
    return result
}