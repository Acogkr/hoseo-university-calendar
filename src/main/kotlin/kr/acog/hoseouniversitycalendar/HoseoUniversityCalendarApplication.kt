package kr.acog.hoseouniversitycalendar

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class HoseoUniversityCalendarApplication

fun main(args: Array<String>) {
    runApplication<HoseoUniversityCalendarApplication>(*args)
}
