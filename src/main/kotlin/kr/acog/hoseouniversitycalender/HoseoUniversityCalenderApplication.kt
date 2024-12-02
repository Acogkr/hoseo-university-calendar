package kr.acog.hoseouniversitycalender

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class HoseoUniversityCalenderApplication

fun main(args: Array<String>) {
    runApplication<HoseoUniversityCalenderApplication>(*args)
}
