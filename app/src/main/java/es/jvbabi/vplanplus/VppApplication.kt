package es.jvbabi.vplanplus

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import dagger.hilt.android.HiltAndroidApp
import es.jvbabi.vplanplus.domain.repository.LogRecordRepository
import es.jvbabi.vplanplus.domain.usecase.KeyValueUseCases
import es.jvbabi.vplanplus.domain.usecase.ProfileUseCases
import es.jvbabi.vplanplus.domain.usecase.SchoolUseCases
import es.jvbabi.vplanplus.domain.usecase.VPlanUseCases
import es.jvbabi.vplanplus.worker.SyncWorker
import javax.inject.Inject

@HiltAndroidApp
class VppApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var profileUseCases: ProfileUseCases

    @Inject
    lateinit var vPlanUseCases: VPlanUseCases

    @Inject
    lateinit var schoolUseCases: SchoolUseCases

    @Inject
    lateinit var logRecordRepository: LogRecordRepository

    @Inject
    lateinit var keyValueUseCases: KeyValueUseCases

    override fun getWorkManagerConfiguration(): Configuration = Configuration.Builder()
        .setWorkerFactory(
            SyncWorkerFactory(
                profileUseCases,
                vPlanUseCases,
                schoolUseCases,
                keyValueUseCases,
                logRecordRepository
            )
        )
        .build()
}

class SyncWorkerFactory @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    private val vPlanUseCases: VPlanUseCases,
    private val schoolUseCases: SchoolUseCases,
    private val keyValueUseCases: KeyValueUseCases,
    private val logRecordRepository: LogRecordRepository
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): SyncWorker {
        return SyncWorker(
            context = appContext,
            params = workerParameters,
            profileUseCases = profileUseCases,
            vPlanUseCases = vPlanUseCases,
            schoolUseCases = schoolUseCases,
            logRecordRepository = logRecordRepository,
            keyValueUseCases = keyValueUseCases
        )
    }
}