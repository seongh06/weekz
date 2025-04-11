package com.weeks.weekz.data.repositoryImpl

import com.weeks.weekz.data.dto.BaseResponse
import com.weeks.weekz.data.dto.request.TestRequest
import com.weeks.weekz.data.dto.response.TestResponse
import com.weeks.weekz.data.service.TestService
import com.weeks.weekz.domain.model.TestModel
import com.weeks.weekz.domain.repository.TestRepository
import com.weeks.weekz.util.network.NetworkResult
import com.weeks.weekz.util.network.handleApi
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor(
    private val testService: TestService
) : TestRepository {
    override suspend fun fetchTest(request: TestRequest): NetworkResult<TestModel> {
        return handleApi({testService.fetchTest(request)}) {response: BaseResponse<TestResponse> -> response.data.toTestModel()} // mapper를 통해 api 결과를 TestModel로 매핑
    }

}