package com.lebocoin.data.repository

import com.lebocoin.data.database.entity.InformationEntity
import com.lebocoin.data.datasource.InformationLocalDS
import com.lebocoin.data.datasource.InformationRemoteDS
import com.lebocoin.data.model.InformationDTO
import com.lebocoin.domain.model.ErrorType
import com.lebocoin.domain.model.ResultOf
import com.lebocoin.domain.model.doIfFailure
import com.lebocoin.domain.model.doIfSuccess
import com.lebocoin.domain.repository.InformationRepository
import com.lebocoin.libs.MainCoroutinesRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class InformationRepositoryImplTest {

    @get:Rule
    val coroutinesRule = MainCoroutinesRule()
    private val localDS: InformationLocalDS = mockk()
    private val remoteDS: InformationRemoteDS = mockk()

    private lateinit var repository: InformationRepository

    @Before
    fun setUp() {
        repository = InformationRepositoryImpl(localDS = localDS, remoteDS = remoteDS)
    }

    @Test
    fun `call getRemoteInformation calls localDS insertOrUpdate and return success`() = coroutinesRule.runTest {
        coEvery { remoteDS.getInformation() } returns ResultOf.Success(listOf(InformationDTO(0,0,"","","")))
        coEvery { localDS.insertOrUpdate(any()) } returns 1L
        coEvery { localDS.deleteAll() } returns Unit
        val result = repository.getRemoteInformation()
        assert(result is ResultOf.Success)

        coVerify { localDS.insertOrUpdate(any()) }
        coVerify { localDS.deleteAll() }
        coVerify { remoteDS.getInformation() }
        confirmVerified(localDS)
        confirmVerified(remoteDS)
    }

    @Test
    fun `call getRemoteInformation returns failure`() = coroutinesRule.runTest {
        coEvery { remoteDS.getInformation() } returns ResultOf.Failure(ErrorType.EmptyError)

        val result = repository.getRemoteInformation()
        assert(result is ResultOf.Failure)

        coVerify { remoteDS.getInformation() }
        confirmVerified(remoteDS)
    }

    @Test
    fun `call getInformation returns list of items`() = coroutinesRule.runTest {
        coEvery { localDS.getAll(any(), any()) } returns listOf(InformationEntity(0,0,"","",""))
        val result = repository.getInformation(1, 1)
        assert(result is ResultOf.Success)
        result.doIfSuccess {
            assertTrue(it.isNotEmpty())
        }

        coVerify { localDS.getAll(any(), any()) }
        confirmVerified(localDS)
    }

    @Test
    fun `call getInformation returns failure`() = coroutinesRule.runTest {
        coEvery { localDS.getAll(any(), any()) } returns emptyList()
        val result = repository.getInformation(1, 1)
        assert(result is ResultOf.Failure)
        result.doIfFailure { error ->
            assertTrue(error is ErrorType.EmptyError)
        }

        coVerify { localDS.getAll(any(), any()) }
        confirmVerified(localDS)
    }
}