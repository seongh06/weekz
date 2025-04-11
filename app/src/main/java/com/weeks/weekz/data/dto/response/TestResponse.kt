package  com.weeks.weekz.data.dto.response

import com.weeks.weekz.domain.model.TestModel

data class TestResponse (
    val body: String
){
    fun toTestModel() = TestModel(body)
}