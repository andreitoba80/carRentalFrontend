package com.example.carrentalfrontend.di

import com.example.carrentalfrontend.core.repositories.CarApi
import com.example.carrentalfrontend.core.repositories.CarBrandsApi
import com.example.carrentalfrontend.core.repositories.CarBrandsRepository
import com.example.carrentalfrontend.core.repositories.CarBrandsRepositoryImpl
import com.example.carrentalfrontend.core.repositories.CarRepository
import com.example.carrentalfrontend.core.repositories.CarRepositoryImpl
import com.example.carrentalfrontend.core.repositories.ProtoRepository
import com.example.carrentalfrontend.core.repositories.UserApi
import com.example.carrentalfrontend.core.repositories.UserRepository
import com.example.carrentalfrontend.core.repositories.UserRepositoryImpl
import com.example.carrentalfrontend.ui.viewmodel.AdminScreenViewModel
import com.example.carrentalfrontend.ui.viewmodel.EditUserViewModel
import com.example.carrentalfrontend.ui.viewmodel.HomeFragmentViewModel
import com.example.carrentalfrontend.ui.viewmodel.LoginViewModel
import com.example.carrentalfrontend.ui.viewmodel.ManageCarBrandsViewModel
import com.example.carrentalfrontend.ui.viewmodel.RegisterViewModel
import com.example.carrentalfrontend.ui.viewmodel.SplashScreenViewModel
import com.example.carrentalfrontend.ui.viewmodel.UserListViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        listOf(
            appModule,
            networkModule,
            repositoryModule
        )
    )
}

val appModule = module {
    viewModel {
        LoginViewModel(
            userRepository = get(),
            protoRepository = get()
        )
    }
    viewModel {
        RegisterViewModel(userRepository = get())
    }
    viewModel {
        SplashScreenViewModel(
            protoRepository = get()
        )
    }
    viewModel {
        HomeFragmentViewModel(
            protoRepository = get(),
            carRepository = get()
        )
    }
    viewModel {
        AdminScreenViewModel(
            protoRepository = get()
        )
    }
    viewModel {
        UserListViewModel(
            userRepository = get()
        )
    }
    viewModel {
        ManageCarBrandsViewModel(
            carBrandsRepository = get()
        )
    }
    viewModel {
        EditUserViewModel(
            userRepository = get()
        )
    }
}

val repositoryModule = module {
    single<ProtoRepository> {
        ProtoRepository(context = get())
    }
    single<UserRepository> {
        UserRepositoryImpl(userApi = get())
    }
    single<CarRepository> {
        CarRepositoryImpl(carApi = get())
    }
    single<CarBrandsRepository> {
        CarBrandsRepositoryImpl(carBrandsApi = get())
    }
}

val gson: Gson = GsonBuilder()
    .setLenient()
    .create()

val networkModule = module {
    factory {
        OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8090/api/")
            .client(get())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(UserApi::class.java)
    } bind (UserApi::class)

    single {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8090/api/")
            .client(get())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(CarApi::class.java)
    } bind (CarApi::class)

    single {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8090/api/")
            .client(get())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(CarBrandsApi::class.java)
    } bind (CarBrandsApi::class)
}