package kz.example.android.primitivetjournal.di

import kz.example.android.primitivetjournal.core.Constants
import kz.example.android.primitivetjournal.datasource.remote.PostService
import kz.example.android.primitivetjournal.datasource.remote.createService
import kz.example.android.primitivetjournal.repository.feed.PostRepository
import kz.example.android.primitivetjournal.repository.feed.PostRepositoryImpl
import kz.example.android.primitivetjournal.ui.postFeed.viewModel.PostFeedViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val postModule = module {
    single { createService<PostService>(get(),Constants.BASE_URL) }
    single { PostRepositoryImpl(get()) as PostRepository }
    viewModel { PostFeedViewModel(get()) }
}