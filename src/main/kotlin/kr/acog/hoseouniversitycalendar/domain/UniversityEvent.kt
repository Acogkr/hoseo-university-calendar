package kr.acog.hoseouniversitycalendar.domain

import kotlinx.serialization.Serializable

/**
 * Create by. Acog
 */
@Serializable
data class UniversityEvent(
    val start: String,
    val end: String,
    val description: String
)

