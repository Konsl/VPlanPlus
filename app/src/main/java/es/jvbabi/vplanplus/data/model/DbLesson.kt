package es.jvbabi.vplanplus.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.UUID

@Entity(
    tableName = "lesson",
    foreignKeys = [
        ForeignKey(
            entity = DbClass::class,
            parentColumns = ["classId"],
            childColumns = ["classLessonRefId"],
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class DbLesson(
    @PrimaryKey(autoGenerate = true) val lessonId: Long = 0,
    val lessonNumber: Int,
    val changedSubject: String?,
    val classLessonRefId: Long,
    val defaultLessonId: UUID?,
    val info: String?,
    val roomIsChanged: Boolean,
    val day: LocalDate,
    val version: Long
)