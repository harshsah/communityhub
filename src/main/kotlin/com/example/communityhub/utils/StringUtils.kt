package com.example.communityhub.utils

object StringUtils {

}
fun String.isAlphaNumeric(): Boolean = this.matches("[a-zA-Z0-9]+".toRegex())