package com.lucas.weekz.presentation.ui.schedule

import androidx.compose.animation.core.copy
import com.lucas.weekz.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectScheduleViewModel @Inject constructor(
) : BaseViewModel<SelectScheduleContract.MainViewState, SelectScheduleContract.MainSideEffect, SelectScheduleContract.MainEvent>(
    initialState = SelectScheduleContract.MainViewState()
) {
    init {
        updateState {
            copy(scheduleList = initScheduleList())
        }
    }
    private fun initScheduleList(): List<ScheduleData> {
        val frameCount = 10
        return (1..frameCount).map {
            ScheduleData(
                title = "예시 제목 $it",
                date = "2025.00.00 ~ 2025.00.00",
                time = "24:00",
                location = "예시 장소 $it",
                memo = "예시 메모 $it"
            )
        }
    }

    override fun handleEvents(event: SelectScheduleContract.MainEvent) {
        when (event) {
            SelectScheduleContract.MainEvent.FinishedCreateActivity -> sendEffect({ SelectScheduleContract.MainSideEffect.RefreshScreen })
        }
    }
    fun getScheduleList(): List<ScheduleData> {
        return viewState.value.scheduleList
    }
    fun getScheduleListCount(): Int {
        return viewState.value.scheduleList.count()
    }
}