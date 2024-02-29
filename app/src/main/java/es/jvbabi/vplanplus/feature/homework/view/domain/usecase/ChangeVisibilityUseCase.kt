package es.jvbabi.vplanplus.feature.homework.view.domain.usecase

import es.jvbabi.vplanplus.feature.homework.shared.domain.model.Homework
import es.jvbabi.vplanplus.feature.homework.shared.domain.repository.HomeworkModificationResult
import es.jvbabi.vplanplus.feature.homework.shared.domain.repository.HomeworkRepository

class ChangeVisibilityUseCase(
    private val homeworkRepository: HomeworkRepository
) {

    suspend operator fun invoke(homework: Homework): HomeworkModificationResult {
        return homeworkRepository.changeVisibility(homework)
    }
}