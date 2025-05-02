package com.lucas.weekz.presentation.ui.schedule

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.lucas.weekz.data.dto.ScheduleDto
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor() : ViewModel() {

    val scheduleList = mutableListOf<ScheduleDto>()
    init {
        Log.d("ScheduleViewModel", "ScheduleViewModel Entered")
        scheduleList.add(ScheduleDto(1, "예시 제목 1", "2024.01.01 ~ 2024.01.02", "10:00", "예시 장소 1", "예시 메모 1"))
        scheduleList.add(ScheduleDto(2, "예시 제목 2", "2024.01.03", "14:00", "예시 장소 2", "예시 메모 2"))
        scheduleList.add(ScheduleDto(3, "예시 제목 3", "2024.01.04 ~ 2024.01.05", "18:00", "예시 장소 3", "예시 메모 3"))
        scheduleList.add(ScheduleDto(4, "예시 제목 4", "2024.01.06", "20:00", "예시 장소 4", "예시 메모 4"))
    }

    var selectSchedule: MutableState<ScheduleDto?> = mutableStateOf(null)
        private set
    var title = mutableStateOf("")
        private set
    var date = mutableStateOf("")
        private set
    var time = mutableStateOf("")
        private set
    var location = mutableStateOf("")
        private set
    var memo = mutableStateOf("")
        private set

    fun setSelectSchedule(schedule: ScheduleDto) {
        Log.d("ScheduleViewModel", "setSelectSchedule : $schedule")
        selectSchedule.value = schedule
    }

    fun getSelectSchedule() : ScheduleDto?{
        Log.d("ScheduleViewModel", "getSelectSchedule : ${selectSchedule.value}")
        return selectSchedule.value
    }

    fun updateScheduleData(newTitle:String, newDate:String, newTime:String, newLocation:String, newMemo:String){
        title.value = newTitle
        date.value = newDate
        time.value = newTime
        location.value = newLocation
        memo.value = newMemo
    }

    fun getScheduleListCount(): Int {
        return scheduleList.size
    }

    fun updateTitleData(newTitle: String) {
        title.value = newTitle
    }
    fun updateDateData(newDate: String) {
        date.value = newDate
    }
    fun updateTimeData(newTime: String) {
        time.value = newTime
    }
    fun updateLocationData(newLocation: String) {
        location.value = newLocation
    }
    fun updateMemoData(newMemo: String) {
        memo.value = newMemo
    }
}