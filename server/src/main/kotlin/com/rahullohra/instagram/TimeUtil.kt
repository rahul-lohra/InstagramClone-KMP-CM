package com.rahullohra.instagram

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object TimeUtil {
    fun getCurrentTime() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
}