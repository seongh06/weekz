package com.lucas.weekz.presentation.ui.schedule

import com.lucas.weekz.presentation.base.ViewEvent
import com.lucas.weekz.presentation.base.ViewSideEffect
import com.lucas.weekz.presentation.base.ViewState

data class ScheduleData(
    val title: String,
    val date: String,
    val time: String,
    val location: String,
    val memo: String
)
sealed class SelectScheduleContract {
    data class MainViewState(
        val scheduleList: List<ScheduleData> = emptyList(),
    ) : ViewState

    sealed class MainSideEffect : ViewSideEffect {
        object RefreshScreen : MainSideEffect()
    }

    sealed class MainEvent : ViewEvent {
        object FinishedCreateActivity : MainEvent()
    }
}