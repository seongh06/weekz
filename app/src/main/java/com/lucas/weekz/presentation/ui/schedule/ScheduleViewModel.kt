package com.lucas.weekz.presentation.ui.schedule

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.lucas.weekz.data.dto.ScheduleDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val apiKey: String
) : ViewModel() {
    val scheduleList = mutableListOf<ScheduleDto>()
    init {
        Log.d("ScheduleViewModel", "API Key Loaded from BuildConfig: $apiKey")
    }

    // API 호출 로딩 상태
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    // API 호출 성공/실패 상태
    private val _apiCallSuccess = mutableStateOf(true) // 초기값은 true로 설정
    val apiCallSuccess: State<Boolean> = _apiCallSuccess

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

    /// Gemini API 호출 함수
    fun generateScheduleSummary(scheduleText: String) {
        if (apiKey.isNullOrEmpty()) {
            Log.e("ScheduleViewModel", "API Key not loaded.")
            // API 키 로드 실패 시 처리
            _apiCallSuccess.value = false // API 호출 실패 상태로 설정
            return
        }

        _isLoading.value = true // 로딩 시작
        _apiCallSuccess.value = true // 새로운 호출 시작 시 성공 상태로 재설정

        viewModelScope.launch {
            try {
                val generativeModel = GenerativeModel(
                    modelName = "gemini-1.5-flash", // 또는 현재 유효한 다른 모델 이름
                    apiKey = apiKey
                )

                val prompt = "${scheduleText}\n 해당 공지사항을 캘린더에 넣을거라서 title, date, time, location, memo 로 각각 한줄로 정리해줘. time은 00:00 형식으로 넘겨주고 날짜도 2025.00.00, 장소는 도로명으로 넘겨줘. 메모는 최대 20글자로 해주세요. 그리고 형식은 title: 제목 date: 날짜 time: 00:00 location: 장소 memo: 메모 이런식으로 해줘" +
                        "" // 사용자 입력 텍스트 + 프롬프트 추가

                val response = generativeModel.generateContent(prompt)

                val summarizedText = response.text
                Log.d("ScheduleViewModel", "Summarized Text: $summarizedText")

                val parsedSchedule = parseGeminiResponse(summarizedText)

                if (parsedSchedule != null) {
                    val newId = (scheduleList.maxByOrNull { it.id }?.id ?: 0) + 1
                    val newSchedule = ScheduleDto(
                        id = newId,
                        title = parsedSchedule.title,
                        date = parsedSchedule.date,
                        time = parsedSchedule.time,
                        location = parsedSchedule.location,
                        memo = parsedSchedule.memo
                    )
                    scheduleList.add(newSchedule)
                    Log.d("ScheduleViewModel", "New schedule added: $newSchedule")
                    _apiCallSuccess.value = true // API 호출 및 파싱 성공
                } else {
                    Log.e("ScheduleViewModel", "Failed to parse Gemini response.")
                    _apiCallSuccess.value = false // 파싱 실패
                }

            } catch (e: Exception) {
                Log.e("ScheduleViewModel", "Error calling Gemini API", e)
                _apiCallSuccess.value = false // API 호출 자체 실패
            } finally {
                _isLoading.value = false // 로딩 종료
            }
        }
    }

    private fun parseGeminiResponse(responseText: String?): ScheduleDto? {
        if (responseText.isNullOrBlank()) {
            return null
        }

        var title = ""
        var date = ""
        var time = ""
        var location = ""
        var memo = ""

        // 응답 텍스트를 줄 단위로 분리
        val lines = responseText.split("\n")

        var currentParsingSection: String? = null // 현재 파싱 중인 섹션 (Title, Date, Time 등)

        for (line in lines) {
            val trimmedLine = line.trim()

            when {
                // 볼드체 마크다운 없이 일반 텍스트로 시작하는지 확인
                trimmedLine.startsWith("title:") -> {
                    title = trimmedLine.substringAfter("title:").trim()
                    currentParsingSection = "Title"
                }
                trimmedLine.startsWith("date:") -> {
                    date = trimmedLine.substringAfter("date:").trim()
                    currentParsingSection = "Date"
                }
                trimmedLine.startsWith("time:") -> {
                    time = trimmedLine.substringAfter("time:").trim()
                    currentParsingSection = "Time"
                }
                trimmedLine.startsWith("location:") -> {
                    location = trimmedLine.substringAfter("location:").trim()
                    currentParsingSection = "Location"
                }
                trimmedLine.startsWith("memo:") -> {
                    memo = trimmedLine.substringAfter("memo:").trim()
                    currentParsingSection = "Memo"
                }
                trimmedLine.startsWith("---") -> {
                    // 구분선 무시
                    currentParsingSection = null
                }
                // 이전 섹션에 이어서 내용을 추가 (Time, Location, Memo)
                currentParsingSection == "Time" && trimmedLine.isNotEmpty() -> {
                    // 시간 정보가 여러 줄일 경우 이어붙이기
                    if (!trimmedLine.startsWith("*") && !trimmedLine.contains(":")) { // * 목록이나 새로운 필드 시작 제외
                        time += "\n$trimmedLine"
                    }
                }
                currentParsingSection == "Location" && trimmedLine.isNotEmpty() -> {
                    // 장소 정보가 여러 줄일 경우 이어붙이기
                    if (!trimmedLine.startsWith("*") && !trimmedLine.contains(":")) { // * 목록이나 새로운 필드 시작 제외
                        location += "\n$trimmedLine"
                    }
                }
                currentParsingSection == "Memo" && trimmedLine.isNotEmpty() -> {
                    // 메모 정보가 여러 줄일 경우 이어붙이기
                    // 마크다운 목록('*')이나 볼드체('**') 제거를 고려하여 처리
                    val cleanedMemoLine = trimmedLine
                        .replace("*", "")
                        .replace("**", "")
                        .trim()
                    if (cleanedMemoLine.isNotEmpty() && !cleanedMemoLine.contains(":")) { // 새로운 필드 시작 제외
                        memo += "\n$cleanedMemoLine"
                    }
                }
                // 추가적인 예외 처리 또는 파싱 로직 필요시 여기에 추가
            }
        }

        // 추출된 데이터로 ScheduleDto 객체를 생성
        // 모든 필수 정보가 추출되었는지 확인하는 로직을 추가할 수 있습니다.
        return ScheduleDto(
            id = 0, // ID는 나중에 추가할 때 부여합니다.
            title = title.trim(),
            date = date.trim(),
            time = time.trim(),
            location = location.trim(),
            memo = memo.trim()
        ).takeIf { it.title.isNotEmpty() || it.date.isNotEmpty() || it.time.isNotEmpty() || it.location.isNotEmpty() || it.memo.isNotEmpty() } // 최소한 하나의 필드라도 추출되었다면 객체 반환
    }
}