package com.localbusiness.helper.utils

import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

object DateUtils {

    private val displayFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    private val parsers = listOf(
        DateTimeFormatter.ofPattern("dd/MM/yyyy"),
        DateTimeFormatter.ofPattern("dd-MM-yyyy"),
        DateTimeFormatter.ofPattern("MM/dd/yyyy"),
        DateTimeFormatter.ofPattern("yyyy-MM-dd"),
        DateTimeFormatter.ofPattern("dd MMM yyyy"),
        DateTimeFormatter.ofPattern("d/M/yyyy"),
        DateTimeFormatter.ofPattern("d-M-yyyy"),
    )

    fun formatDate(timestamp: Long): String {
        if (timestamp == 0L) return ""
        return try {
            val instant = Instant.ofEpochMilli(timestamp)
            val date = LocalDate.ofInstant(instant, ZoneId.systemDefault())
            displayFormatter.format(date)
        } catch (e: Exception) {
            ""
        }
    }

    fun parseDate(dateStr: String): Long {
        if (dateStr.isEmpty()) return 0L
        for (formatter in parsers) {
            try {
                val date = LocalDate.parse(dateStr.trim(), formatter)
                return date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            } catch (e: Exception) {
                continue
            }
        }
        return 0L
    }

    fun todayRange(): Pair<Long, Long> {
        val today = LocalDate.now(ZoneId.systemDefault())
        val start = today.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val end = today.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        return Pair(start, end)
    }

    fun longToLocalDate(timestamp: Long): LocalDate? {
        if (timestamp == 0L) return null
        return try {
            Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate()
        } catch (e: Exception) {
            null
        }
    }

    fun localDateToLong(date: LocalDate): Long {
        return date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }
}
