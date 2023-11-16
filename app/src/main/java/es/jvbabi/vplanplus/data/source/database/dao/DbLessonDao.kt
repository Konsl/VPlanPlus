package es.jvbabi.vplanplus.data.source.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import es.jvbabi.vplanplus.data.model.DbLesson
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
abstract class DbLessonDao {

    @Query("SELECT * FROM lesson WHERE classLessonRefId = :classId AND day = :timestamp")
    abstract fun getLessonsByClass(classId: Long, timestamp: LocalDate): Flow<List<DbLesson>>

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM lesson LEFT JOIN lesson_teacher_crossover ON lesson.lessonId = lesson_teacher_crossover.ltcLessonId WHERE lesson_teacher_crossover.ltcTeacherId = :teacherId AND lesson.day = :timestamp")
    abstract fun getLessonsByTeacher(teacherId: Long, timestamp: LocalDate): Flow<List<DbLesson>>

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM lesson LEFT JOIN lesson_room_crossover ON lesson.lessonId = lesson_room_crossover.lrcLessonId AND lesson_room_crossover.lrcRoomId = :roomId AND day = :timestamp")
    abstract fun getLessonsByRoom(roomId: Long, timestamp: LocalDate): Flow<List<DbLesson>>

    @Insert
    abstract suspend fun insertLesson(lesson: DbLesson): Long

    @Query("DELETE FROM lesson")
    abstract suspend fun deleteAll()

    @Query("DELETE FROM lesson WHERE classLessonRefId = :classId AND day = :timestamp")
    abstract suspend fun deleteLessonsByClassAndDate(classId: Long, timestamp: LocalDate)
}