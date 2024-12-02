package kr.acog.hoseouniversitycalender.service

import kotlinx.serialization.json.Json
import kr.acog.hoseouniversitycalender.domain.UniversityEvent
import net.fortuna.ical4j.data.CalendarOutputter
import net.fortuna.ical4j.model.Calendar
import net.fortuna.ical4j.model.DateTime
import net.fortuna.ical4j.model.component.VEvent
import net.fortuna.ical4j.model.property.Method
import net.fortuna.ical4j.model.property.ProdId
import net.fortuna.ical4j.model.property.Version
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileOutputStream

@Service
class CalendarService {

    private val eventFile = File("src/main/resources/static/hoseo-university-event.json")
    private val calendarFile = File("src/main/resources/static/hoseo-university-calendar.ics")

    fun getCalendarFile(): File {
        if (!calendarFile.exists()) {
            updateCalendar()
        }
        return calendarFile
    }

    fun updateCalendar() {
        val events: List<UniversityEvent> = Json.decodeFromString(eventFile.readText())
        val calendar = Calendar().apply {
            properties.add(ProdId("-//Hoseo University//Calendar 1.0//KR"))
            properties.add(Version.VERSION_2_0)
            properties.add(Method.PUBLISH)
        }

        events.forEach { (start, end, description) ->
            val startTime = DateTime(start.replace("-", "") + "T000000")
            val endTime = DateTime(end.replace("-", "") + "T000000")
            calendar.components.add(
                VEvent(startTime, endTime, description)
            )
        }

        FileOutputStream(calendarFile).use { outputStream ->
            CalendarOutputter().apply {
                isValidating = false
            }.output(calendar, outputStream)
        }
    }

}
