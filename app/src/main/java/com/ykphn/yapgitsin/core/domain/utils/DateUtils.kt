package com.ykphn.yapgitsin.core.domain.utils

import kotlin.time.Instant                           // kotlin.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.time.ExperimentalTime

@Suppress("DEPRECATION")
@OptIn(ExperimentalTime::class)
fun formatToMonthYear(rawDate: Instant?): String {
    return try {
        if (rawDate == null) return "Bilinmeyen Tarih"

        // 1) kotlin.time.Instant → epoch ms
        val epochMs = rawDate.toEpochMilliseconds()

        // 2) epoch ms → java.time.Instant
        val javaInst = java.time.Instant.ofEpochMilli(epochMs)

        // 3) zoned date-time
        val zdt = javaInst.atZone(ZoneId.systemDefault())

        // 4) "MMMM yyyy" format, Türkçe locale
        val fmt = DateTimeFormatter.ofPattern("MMMM yyyy", Locale("tr"))
        zdt.format(fmt)
    } catch (_: Exception) {
        "Bilinmeyen Tarih"
    }
}



