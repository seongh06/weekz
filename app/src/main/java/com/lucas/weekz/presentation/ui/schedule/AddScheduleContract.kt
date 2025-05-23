package com.lucas.weekz.presentation.ui.schedule

import com.lucas.weekz.presentation.base.ViewEvent
import com.lucas.weekz.presentation.base.ViewSideEffect
import com.lucas.weekz.presentation.base.ViewState

class AddScheduleContract {
    object MainViewState : ViewState

    sealed class MainSideEffect : ViewSideEffect {
        object RefreshScreen : MainSideEffect()
    }

    sealed class MainEvent : ViewEvent {
        object FinishedCreateActivity : MainEvent()
    }
}
