package de.esd.zeiterfassung.persistence

import java.lang.RuntimeException

class ConstraintViolationException constructor(message: String) : RuntimeException(message) {

    val exceptionMessage: String = message


}