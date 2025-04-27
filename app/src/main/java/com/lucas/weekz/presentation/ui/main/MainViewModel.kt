package com.lucas.weekz.presentation.ui.main

import com.lucas.weekz.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
) : BaseViewModel<MainContract.MainViewState, MainContract.MainSideEffect, MainContract.MainEvent>(MainContract.MainViewState) {
    override fun handleEvents(event: MainContract.MainEvent) {
        when (event) {
            MainContract.MainEvent.FinishedCreateActivity -> sendEffect({ MainContract.MainSideEffect.RefreshScreen })
        }
    }
}
