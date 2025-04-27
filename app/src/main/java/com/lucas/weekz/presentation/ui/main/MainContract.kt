package com.lucas.weekz.presentation.ui.main

import com.lucas.weekz.presentation.base.ViewEvent
import com.lucas.weekz.presentation.base.ViewSideEffect
import com.lucas.weekz.presentation.base.ViewState

class MainContract {
    object MainViewState : ViewState

    sealed class MainSideEffect : ViewSideEffect {
        object RefreshScreen : MainSideEffect()
    }

    sealed class MainEvent : ViewEvent {
        object FinishedCreateActivity : MainEvent()
    }
}
