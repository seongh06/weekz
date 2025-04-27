package com.lucas.weekz.presentation.ui.schedule

import com.lucas.weekz.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AddScheduleViewModel @Inject constructor(
) : BaseViewModel<AddScheduleContract.MainViewState, AddScheduleContract.MainSideEffect, AddScheduleContract.MainEvent>(AddScheduleContract.MainViewState) {
    override fun handleEvents(event: AddScheduleContract.MainEvent) {
        when (event) {
            AddScheduleContract.MainEvent.FinishedCreateActivity -> sendEffect({ AddScheduleContract.MainSideEffect.RefreshScreen })
        }
    }
}
