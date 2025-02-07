package com.lebocoin.data.datasource

import com.lebocoin.data.model.InformationDTO
import com.lebocoin.data.service.ApiService
import com.lebocoin.domain.model.doIfSuccess
import com.lebocoin.libs.MainCoroutinesRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class InformationRemoteDSTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutinesRule = MainCoroutinesRule()

    private val apiService: ApiService = mockk()

    private lateinit var dataSource: InformationRemoteDS

    @Before
    fun setup() {
        dataSource = InformationRemoteDS(apiService)
    }


    @Test
    fun `call getInformation returns list of items`() = coroutinesRule.runTest {
        coEvery { apiService.getInformation() } returns Response.success(listOf(InformationDTO(0, 0, "", "", "")))
        dataSource.getInformation().doIfSuccess {
            assert(it.isNotEmpty())

        }
        coVerify { apiService.getInformation() }
        confirmVerified(apiService)
    }

}