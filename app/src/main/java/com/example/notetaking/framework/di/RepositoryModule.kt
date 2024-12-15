package com.example.notetaking.framework.di

import android.content.Context
import com.example.core.repository.NoteRepository
import com.example.notetaking.framework.RoomNoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNoteRepository(
        @ApplicationContext context: Context
    ) = NoteRepository(RoomNoteDataSource(context))
}