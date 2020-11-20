package kz.example.android.primitivetjournal.di

import kz.example.android.primitivetjournal.core.Constants
import kz.example.android.primitivetjournal.datasource.remote.UserService
import kz.example.android.primitivetjournal.datasource.remote.createService
import kz.example.android.primitivetjournal.repository.user.UserRepository
import kz.example.android.primitivetjournal.repository.user.UserRepositoryImpl
import kz.example.android.primitivetjournal.ui.profile.UserViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {
    single { createService<UserService>(get(), Constants.BASE_URL) }
    single { UserRepositoryImpl(get(),get()) as UserRepository }
    viewModel { UserViewModel(get()) }
}