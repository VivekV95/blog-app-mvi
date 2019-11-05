package com.example.blogappmvi.di

import android.app.Application
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.blogappmvi.R
import com.example.blogappmvi.persistence.AccountPropertiesDao
import com.example.blogappmvi.persistence.AppDatabase
import com.example.blogappmvi.persistence.AppDatabase.Companion.DATABASE_NAME
import com.example.blogappmvi.persistence.AuthTokenDao
import com.example.blogappmvi.util.Constants.Companion.BASE_URL
import com.example.blogappmvi.util.LiveDataCallAdapter
import com.example.blogappmvi.util.LiveDataCallAdapterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule{

    @Singleton
    @Provides
    fun provideGsonBuilder() =
        GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()

    @Singleton
    @Provides
    fun provideRetrofitInstance(gson: Gson) =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Singleton
    @Provides
    fun provideAppDb(app: Application): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration() // get correct db version if schema changed
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthTokenDao(db: AppDatabase): AuthTokenDao {
        return db.getAuthTokenDao()
    }

    @Singleton
    @Provides
    fun provideAccountPropertiesDao(db: AppDatabase): AccountPropertiesDao {
        return db.getAccountPropertiesDao()
    }

    @Singleton
    @Provides
    fun provideRequestOptions(): RequestOptions {
        return RequestOptions
            .placeholderOf(R.drawable.default_image)
            .error(R.drawable.default_image)
    }

    @Singleton
    @Provides
    fun provideGlideInstance(application: Application, requestOptions: RequestOptions): RequestManager {
        return Glide.with(application)
            .setDefaultRequestOptions(requestOptions)
    }

}