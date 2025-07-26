package com.example.composechat.di

import android.content.Context
import com.example.composechat.data.network.FirebaseChatService
import com.example.composechat.data.network.database.DatabaseServiceImpl
import com.example.composechat.domain.DatabaseService
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideDatabaseReference() = Firebase.database.reference

    @Singleton
    @Provides
    fun provideFirebaseService(reference: DatabaseReference) =
        FirebaseChatService(reference = reference)

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context): DatabaseService {
        return DatabaseServiceImpl(context)
    }
}