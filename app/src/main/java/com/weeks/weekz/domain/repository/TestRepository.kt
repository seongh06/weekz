package com.weeks.weekz.domain.repository

import com.weeks.weekz.data.dto.request.TestRequest
import com.weeks.weekz.domain.model.TestModel
import com.weeks.weekz.util.network.NetworkResult


interface TestRepository {
    suspend fun fetchTest(request: TestRequest): NetworkResult<TestModel>
}