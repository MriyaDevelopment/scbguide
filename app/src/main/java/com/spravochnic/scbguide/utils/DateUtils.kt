package com.spravochnic.scbguide.utils

import android.content.Context
import android.os.Parcelable
import androidx.core.util.toAndroidXPair
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.gson.*
import kotlinx.parcelize.Parcelize
import java.lang.reflect.Type
import java.time.*
import java.time.format.DateTimeFormatter

inline fun FragmentManager.showDatePickerDialog(
    value: LocalDateFormatter,
    validator: CalendarConstraints.DateValidator = DateValidatorPointForward.now(),
    start: LocalDateFormatter = LocalDateFormatter.today(),
    end: LocalDateFormatter = start.update { plusYears(1L) },
    crossinline listener: (LocalDateFormatter) -> Unit
) {
    val constraintsBuilder =
        CalendarConstraints.Builder()
            .setValidator(validator)
            .setStart(start.timeInMillis())
            .setEnd(end.timeInMillis())
    val datePicker =
        MaterialDatePicker.Builder.datePicker()
            .setCalendarConstraints(constraintsBuilder.build())
            .setSelection(value.timeInMillis())
            .build()
    datePicker.addOnPositiveButtonClickListener { date ->
        listener(
            LocalDateFormatter.from(date).update {
                value.localDateTime.let { withHour(it.hour).withMinute(it.minute) }
            }
        )
    }
    datePicker.show(this, null)
}

inline fun FragmentManager.showDateRangePickerDialog(
    value: Pair<LocalDateFormatter, LocalDateFormatter>,
    validator: CalendarConstraints.DateValidator = DateValidatorPointForward.now(),
    start: LocalDateFormatter = LocalDateFormatter.today(),
    end: LocalDateFormatter = start.update { plusYears(1L) },
    crossinline listener: (Pair<LocalDateFormatter, LocalDateFormatter>) -> Unit
) {
    val constraintsBuilder =
        CalendarConstraints.Builder()
            .setValidator(validator)
            .setStart(start.timeInMillis())
            .setEnd(end.timeInMillis())
            .build()

    val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
        .setCalendarConstraints(constraintsBuilder)
        .setSelection(
            value.let { (first, second) ->
                Pair(first.timeInMillis(), second.timeInMillis()).toAndroidXPair()
            }
        )
        .build()

    dateRangePicker.addOnPositiveButtonClickListener { range ->
        val from = LocalDateFormatter.from(range.first).update {
            value.first.localDateTime.let { withHour(it.hour).withMinute(it.minute) }
        }
        val to = LocalDateFormatter.from(range.second).update {
            value.second.localDateTime.let { withHour(it.hour).withMinute(it.minute) }
        }

        listener.invoke(Pair(from, to))
    }
    dateRangePicker.show(this, null)
}

inline fun FragmentManager.showTimePickerDialog(
    value: LocalDateFormatter,
    crossinline listener: (LocalDateFormatter) -> Unit
) {
    val timePicker = MaterialTimePicker.Builder()
        .setTimeFormat(TimeFormat.CLOCK_24H)
        .setHour(value.localDateTime.hour)
        .setMinute(value.localDateTime.minute)
        .build()
    timePicker.addOnPositiveButtonClickListener {
        listener(value.update { withHour(timePicker.hour).withMinute(timePicker.minute) })
    }
    timePicker.show(this, null)
}

fun String?.toLocalDateFormatter() = runCatching {
    LocalDateFormatter(LocalDateTime.ofInstant(Instant.parse(this), ZoneId.systemDefault()))
}.getOrNull()

fun LocalDate.localDateToLocalDateFormatter(): LocalDateFormatter {
    return LocalDateFormatter(this.atStartOfDay())
}

typealias LocalDateFormatterRange = Pair<LocalDateFormatter, LocalDateFormatter>

@Parcelize
@Suppress("FunctionName")
class LocalDateFormatter(
    val localDateTime: LocalDateTime
) : Parcelable, Comparable<LocalDateFormatter> {
    //  utcOffset == null получаем время установленное на устройстве иначе задаем смещение по utc
    fun format(pattern: String, utcOffset: Int? = null) = if (utcOffset == null) {
        localDateTime.format(DateTimeFormatter.ofPattern(pattern))
    } else {
        localDateTime.atZone(ZoneId.systemDefault()).toInstant()
            .atOffset(ZoneOffset.ofHours(utcOffset)).format(DateTimeFormatter.ofPattern(pattern))
    }

    fun d() = format("d")
    fun HH_mm(utcOffset: Int? = null) = format("HH:mm", utcOffset)
    fun dd_MM_yyyy() = format("dd.MM.yyyy")
    fun d_M_yyyy() = format("d.M.yyyy")
    fun dd_MM_yy() = format("dd.MM.yy")
    fun dd_MMMM() = format("dd MMMM")
    fun MMMM_yyyy() = format("MMMM yyyy")
    fun yyyy() = format("yyyy")
    fun dd_MMM() = format("dd MMM")
    fun MMMM() = format("MMMM")
    fun dd_MMMM_HH_mm() = format("dd MMMM HH:mm")
    fun dd_MMMM_yyyy() = format("dd MMMM yyyy")
    fun yyyy_MM_dd_T_HH_mm_ss() = format("yyyy-MM-dd'T'HH:mm:ss")

    fun timeInMillis() = localDateTime.atZone(ZoneOffset.UTC).toInstant().toEpochMilli()
    fun toIsoUTC(): String = localDateTime.atZone(ZoneId.systemDefault()).toInstant()
        .atOffset(ZoneOffset.ofHours(0))
        .format(DateTimeFormatter.ofPattern(ISO_FORMAT))

    inline fun update(block: LocalDateTime.() -> LocalDateTime): LocalDateFormatter {
        return LocalDateFormatter(localDateTime.block())
    }

    fun withLocalTime(other: LocalDateFormatter): LocalDateFormatter {
        return LocalDateFormatter(
            localDateTime.withHour(other.localDateTime.hour).withMinute(other.localDateTime.minute)
        )
    }

    fun between(other: LocalDateFormatter): Duration =
        Duration.between(localDateTime, other.localDateTime)

    override fun compareTo(other: LocalDateFormatter): Int {
        return timeInMillis().compareTo(other.timeInMillis())
    }

    override fun equals(other: Any?): Boolean =
        (other is LocalDateFormatter) && this.timeInMillis() == other.timeInMillis()

    fun equalsDate(other: LocalDateFormatter): Boolean =
        localDateTime.year == other.localDateTime.year
                && localDateTime.dayOfYear == other.localDateTime.dayOfYear

    override fun hashCode(): Int {
        return localDateTime.hashCode()
    }

    companion object {
        const val ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

        fun now() = LocalDateFormatter(LocalDateTime.now())
        fun today() = LocalDateFormatter(LocalDateTime.now().toLocalDate().atStartOfDay())
        fun from(millis: Long) = LocalDateFormatter(
            LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault())
        )

        fun parse(string: String?, pattern: String): LocalDateFormatter? {
            val formatter = DateTimeFormatter.ofPattern(pattern)
            val localDateTime: LocalDateTime =
                LocalDate.parse(string, formatter)?.atStartOfDay() ?: return null
            return LocalDateFormatter(localDateTime)
        }
    }
}

class LocalDateFormatterJsonConverter : JsonDeserializer<LocalDateFormatter?>,
    JsonSerializer<LocalDateFormatter> {
    override fun serialize(
        src: LocalDateFormatter?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src?.toIsoUTC())
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDateFormatter? {
        return json?.asString?.toLocalDateFormatter()
    }
}