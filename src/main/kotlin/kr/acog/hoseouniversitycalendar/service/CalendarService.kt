package kr.acog.hoseouniversitycalendar.service

import jakarta.annotation.PostConstruct
import kotlinx.serialization.json.Json
import kr.acog.hoseouniversitycalendar.domain.UniversityEvent
import net.fortuna.ical4j.data.CalendarOutputter
import net.fortuna.ical4j.model.Calendar
import net.fortuna.ical4j.model.Date
import net.fortuna.ical4j.model.Property
import net.fortuna.ical4j.model.component.VEvent
import net.fortuna.ical4j.model.parameter.Value
import net.fortuna.ical4j.model.property.*
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate

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

    @PostConstruct
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
            calendar.components.add(
                VEvent(
                    Date(LocalDate.parse(start).toString().replace("-", "")),
                    Date(LocalDate.parse(end).plusDays(1).toString().replace("-", "")),
                    description
                ).apply {
                    (getProperty(Property.DTSTART) as DateProperty).parameters.add(Value.DATE)
                    (getProperty(Property.DTEND) as DateProperty).parameters.add(Value.DATE)
                }
            )
        }

        FileOutputStream(calendarFile).use { outputStream ->
            CalendarOutputter().apply {
                isValidating = false
            }.output(calendar, outputStream)
        }
    }

}
