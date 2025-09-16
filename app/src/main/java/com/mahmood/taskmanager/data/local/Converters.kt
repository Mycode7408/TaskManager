package com.mahmood.taskmanager.data.local

import androidx.room.TypeConverter
import com.mahmood.taskmanager.domain.entity.Priority

class Converters {
    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}
