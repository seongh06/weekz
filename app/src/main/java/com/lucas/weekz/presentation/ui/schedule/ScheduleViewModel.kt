package com.lucas.weekz.presentation.ui.schedule

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.lucas.weekz.data.dto.ScheduleDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val apiKey: String
) : ViewModel() {
    val scheduleList = mutableStateListOf<ScheduleDto>() // Composable에서 바로 관찰 가능
    init {
        Log.d("ScheduleViewModel", "API Key Loaded from BuildConfig: $apiKey")
    }

    // API 호출 로딩 상태
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    // API 호출 성공/실패 상태
    private val _apiCallSuccess = mutableStateOf(true) // 초기값은 true로 설정
    val apiCallSuccess: State<Boolean> = _apiCallSuccess
    private val _scheduleListState = MutableStateFlow<List<ScheduleDto>>(emptyList())
    val scheduleListState: StateFlow<List<ScheduleDto>> = _scheduleListState.asStateFlow()

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
    private val _userMessage = mutableStateOf<String?>(null)
    val userMessage: State<String?> = _userMessage

    fun clearUserMessage() {
        _userMessage.value = null // 메시지를 null로 설정하여 "없음" 상태로 만듦
    }
    private val _navigateToFailScreen = MutableSharedFlow<String?>() // 실패 메시지를 전달할 수 있도록 String? 타입
    val navigateToFailScreen = _navigateToFailScreen.asSharedFlow()

    /// Gemini API 호출 함수
    fun generateScheduleSummary(scheduleText: String) {
        if (apiKey.isNullOrEmpty()) {
            Log.e("ScheduleViewModel", "API Key not loaded.")
            // API 키 로드 실패 시 처리
            _apiCallSuccess.value = false // API 호출 실패 상태로 설정
            return
        }
        _isLoading.value = true
        _apiCallSuccess.value = true // 새로운 호출 시작 시 기본적으로 성공으로 가정
        _userMessage.value = null // 이전 메시지 초기화

        viewModelScope.launch {
            var isSuccessful = false // 최종 성공 여부를 판단할 변수
            var loggableSummarizedText: String? = null // 성공 시 로그에 남길 텍스트

            try {
                val generativeModel = GenerativeModel(
                    modelName = "gemini-1.5-flash",
                    apiKey = apiKey
                )

                val prompt = """
                ${scheduleText}
                해당 공지사항을 캘린더에 넣을 거야. title, date, time, location, memo 항목으로 각각 한 줄로 정리해 줘.
                - title: 제목 (필수)
                - date: 날짜 (YYYY.MM.DD 형식, 예: 2025.01.15) (필수)
                - time: 시간 (HH:MM 형식, 예: 14:30) (필수)
                - location: 장소 (도로명 주소 또는 주요 장소명) (선택, 없으면 비워둠)
                - memo: 메모 (최대 20자) (선택, 없으면 비워둠)

                정확한 형식은 다음과 같아:
                title: [추출된 제목]
                date: [추출된 날짜]
                time: [추출된 시간]
                location: [추출된 장소]
                memo: [추출된 메모]

                만약 위 항목들, 특히 title, date, time 중 하나라도 명확하게 추출할 수 없거나, 입력 내용이 일정과 관련이 없다고 판단되면, 다른 어떤 설명도 없이 오직 "fail" 이라는 단어만 응답해 줘.
                """.trimIndent()
                Log.d("ScheduleViewModel", "Prompt: $prompt")
                val response = generativeModel.generateContent(prompt)
                val summarizedText = response.text?.trim()

                if (summarizedText.equals("fail", ignoreCase = true)) {
                    Log.w("ScheduleViewModel", "Gemini API returned 'fail'. Input might be irrelevant or unparseable.")
                    isSuccessful = false
                } else if (summarizedText.isNullOrBlank()) {
                    Log.e("ScheduleViewModel", "Gemini API returned null or blank response.")
                    isSuccessful = false
                } else {
                    val parsedSchedule = parseGeminiResponse(summarizedText)
                    // 파싱 성공 및 필수 필드 확인 (title, date, time)
                    if (parsedSchedule != null &&
                        parsedSchedule.title.isNotBlank() &&
                        parsedSchedule.date.isNotBlank() &&
                        parsedSchedule.time.isNotBlank()
                    ) {
                        loggableSummarizedText = summarizedText // 성공 시 로그에 남길 텍스트 할당
                        isSuccessful = true
                        viewModelScope.launch { // UI 스레드에서 상태 변경 권장
                            val newId = scheduleList.size + 1 // Int + Int = Int
                            scheduleList.add(parsedSchedule.copy(id = newId)) // id에 Int 할당
                            // 또는 _scheduleListState를 사용한다면
                            // _scheduleListState.value = _scheduleListState.value + parsedSchedule.copy(id = _scheduleListState.value.size + 1L)
                        }
                    } else {
                        Log.e("ScheduleViewModel", "Failed to parse Gemini response or missing required fields. Response: $summarizedText")
                        isSuccessful = false
                    }
                }
            } catch (e: Exception) {
                Log.e("ScheduleViewModel", "Error calling Gemini API or processing response", e)
                isSuccessful = false
            } finally {
                _apiCallSuccess.value = isSuccessful // 최종 성공 여부 반영
                _isLoading.value = false

                if (isSuccessful && loggableSummarizedText != null) {
                    // 성공한 경우에만 로그 출력
                    Log.d("ScheduleViewModel", "Summarized Text: $loggableSummarizedText")
                }
                // 실패 로그는 각 실패 지점에서 이미 출력됨 (Log.w 또는 Log.e)
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