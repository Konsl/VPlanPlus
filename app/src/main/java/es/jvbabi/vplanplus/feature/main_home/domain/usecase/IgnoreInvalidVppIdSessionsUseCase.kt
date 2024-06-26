package es.jvbabi.vplanplus.feature.main_home.domain.usecase

import es.jvbabi.vplanplus.domain.repository.KeyValueRepository
import es.jvbabi.vplanplus.domain.repository.Keys

class IgnoreInvalidVppIdSessionsUseCase(
    private val keyValueRepository: KeyValueRepository
) {
    suspend operator fun invoke() {
        keyValueRepository.set(Keys.INVALID_VPP_SESSION, "false")
    }
}