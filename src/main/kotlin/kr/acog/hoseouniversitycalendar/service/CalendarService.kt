package kr.acog.hoseouniversitycalendar.service

import kotlinx.serialization.json.Json
import kr.acog.hoseouniversitycalendar.domain.UniversityEvent
import net.fortuna.ical4j.data.CalendarOutputter
import net.fortuna.ical4j.model.Calendar
import net.fortuna.ical4j.model.DateTime
import net.fortuna.ical4j.model.component.VEvent
import net.fortuna.ical4j.model.property.Method
import net.fortuna.ical4j.model.property.ProdId
import net.fortuna.ical4j.model.property.Version
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileOutputStream

@Service
class CalendarService {

    private val eventResource = ClassPathResource("static/hoseo-university-event.json")
    private var calendarFile = File(System.getProperty("java.io.tmpdir"), "hoseo-university-calendar.ics")

    fun getCalendarFile(): File {
        if (!calendarFile.exists()) {
            updateCalendar()
        }
        return calendarFile
    }

    fun updateCalendar() {
        val events: List<UniversityEvent> = eventResource.inputStream.bufferedReader().use {
            Json.decodeFromString(it.readText())
        }
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