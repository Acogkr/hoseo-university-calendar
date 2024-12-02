package kr.acog.hoseouniversitycalender.component

import kr.acog.hoseouniversitycalender.service.CalendarService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

/**
 * Create by. Acog
 */
@Component
class CalendarSchedule(
    private val calendarService: CalendarService
) {

    @Scheduled(cron = "0 0 * * * *")
    fun updateCalendar() {
        calendarService.updateCalendar()
    }

}