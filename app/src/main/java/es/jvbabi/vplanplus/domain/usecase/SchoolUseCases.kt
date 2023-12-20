package es.jvbabi.vplanplus.domain.usecase

import es.jvbabi.vplanplus.domain.model.School
import es.jvbabi.vplanplus.domain.repository.SchoolRepository

class SchoolUseCases(
    private val schoolRepository: SchoolRepository
) {

    fun checkSchoolId(schoolId: String): SchoolIdCheckResult {
        return if (schoolId.length == 8 && schoolId.toLongOrNull() != null) SchoolIdCheckResult.SYNTACTICALLY_CORRECT else SchoolIdCheckResult.INVALID
    }

    suspend fun deleteSchool(schoolId: Long) {
        schoolRepository.deleteSchool(schoolId)
    }

    suspend fun checkSchoolIdOnline(schoolId: Long): Flow<SchoolIdCheckResult?> {
        return flowOf(schoolRepository.checkSchoolId(schoolId))
    }

    suspend fun getSchoolByName(schoolName: String): School {
        return schoolRepository.getSchoolByName(schoolName)
    }

    suspend fun getSchools(): List<School> {
        return schoolRepository.getSchools()
    }
}

enum class SchoolIdCheckResult {
    INVALID,
    VALID,
    SYNTACTICALLY_CORRECT,
    NOT_FOUND
}

enum class Response {
    SUCCESS,
    WRONG_CREDENTIALS,
    NO_INTERNET,
    NONE,
    OTHER,
    NOT_FOUND,
    NO_DATA_AVAILABLE
}