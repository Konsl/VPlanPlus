package es.jvbabi.vplanplus.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.jvbabi.vplanplus.data.repository.ProfileRepositoryImpl
import es.jvbabi.vplanplus.data.repository.SchoolRepositoryImpl
import es.jvbabi.vplanplus.data.source.VppDatabase
import es.jvbabi.vplanplus.domain.repository.ProfileRepository
import es.jvbabi.vplanplus.domain.repository.SchoolRepository
import es.jvbabi.vplanplus.domain.usecase.ProfileUseCases
import es.jvbabi.vplanplus.domain.usecase.SchoolUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VppModule {
    @Provides
    @Singleton
    fun provideVppDatabase(app: Application): VppDatabase {
        return Room.databaseBuilder(
            app,
            VppDatabase::class.java,
            "vpp.db"
        )
            .fallbackToDestructiveMigration() // TODO: Remove for production
            .build()
    }

    @Provides
    @Singleton
    fun provideSchoolRepository(db: VppDatabase): SchoolRepository {
        return SchoolRepositoryImpl(db.schoolDao)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(db: VppDatabase): ProfileRepository {
        return ProfileRepositoryImpl(db.profileDao)
    }

    @Provides
    @Singleton
    fun provideSchoolUseCases(repository: SchoolRepository): SchoolUseCases {
        return SchoolUseCases(repository)
    }

    @Provides
    @Singleton
    fun provideProfileUseCases(repository: ProfileRepository): ProfileUseCases {
        return ProfileUseCases(repository)
    }
}