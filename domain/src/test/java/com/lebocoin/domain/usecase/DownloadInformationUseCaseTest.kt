package com.lebocoin.domain.usecase

import app.cash.turbine.test
import com.lebocoin.domain.model.ErrorType
import com.lebocoin.domain.model.Information
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
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DownloadInformationUseCaseTest {

    @get:Rule
    val coroutinesRule = MainCoroutinesRule()

    private val repository: InformationRepository = mockk()

    private lateinit var useCase: DownloadInformationUseCase

    @Before
    fun setUp() {
        useCase = DownloadInformationUseCase(repository, coroutinesRule.testDispatcherProvider)
    }

    @Test
    fun `execute UseCase returns ResultOf Success`() = coroutinesRule.runTest {
        coEvery { repository.getRemoteInformation() } returns ResultOf.Success(Unit)
        useCase.execute(Unit).test {
            val result = awaitItem()
            assert(result is ResultOf.Success)
            result.doIfSuccess {
                assertTrue(it is Unit)
            }
            awaitComplete()
        }
        coVerify { repository.getRemoteInformation() }
        confirmVerified(repository)
    }

    @Test
    fun `execute UseCase returns ResultOf Failure with ErrorType EmptyError`() = coroutinesRule.runTest {
        val errorResponse = ErrorType.EmptyError
        coEvery { repository.getRemoteInformation() } returns ResultOf.Failure(errorResponse)
        useCase.execute(Unit).test {
            val result = awaitItem()
            assert(result is ResultOf.Failure)
            result.doIfFailure { error ->
                assertEquals(error, errorResponse)
            }
            awaitComplete()
        }
        coVerify { repository.getRemoteInformation() }
        confirmVerified(repository)
    }

    @Test
    fun `execute UseCase returns ResultOf Failure with ErrorType ConnectivityError`() = coroutinesRule.runTest {
        val errorResponse = ErrorType.ConnectivityError
        coEvery { repository.getRemoteInformation() } returns ResultOf.Failure(errorResponse)
        useCase.execute(Unit).test {
            val result = awaitItem()
            assert(result is ResultOf.Failure)
            result.doIfFailure { error ->
                assertEquals(error, errorResponse)
            }
            awaitComplete()
        }
        coVerify { repository.getRemoteInformation() }
        confirmVerified(repository)
    }

    @Test
    fun `execute UseCase returns ResultOf Failure with ErrorType UnknownError`() = coroutinesRule.runTest {
        val errorResponse = ErrorType.UnknownError
        coEvery { repository.getRemoteInformation() } returns ResultOf.Failure(errorResponse)
        useCase.execute(Unit).test {
            val result = awaitItem()
            assert(result is ResultOf.Failure)
            result.doIfFailure { error ->
                assertEquals(error, errorResponse)
            }
            awaitComplete()
        }
        coVerify { repository.getRemoteInformation() }
        confirmVerified(repository)
    }

    @Test
    fun `execute UseCase returns ResultOf Failure with ErrorType ServiceError`() = coroutinesRule.runTest {
        val errorResponse = ErrorType.ServiceError
        coEvery { repository.getRemoteInformation() } returns ResultOf.Failure(errorResponse)
        useCase.execute(Unit).test {
            val result = awaitItem()
            assert(result is ResultOf.Failure)
            result.doIfFailure { error ->
                assertEquals(error, errorResponse)
            }
            awaitComplete()
        }
        coVerify { repository.getRemoteInformation() }
        confirmVerified(repository)
    }

    @Test
    fun `execute UseCase returns ResultOf Failure with ErrorType ServerError`() = coroutinesRule.runTest {
        val errorResponse = ErrorType.ServerError
        coEvery { repository.getRemoteInformation() } returns ResultOf.Failure(errorResponse)
        useCase.execute(Unit).test {
            val result = awaitItem()
            assert(result is ResultOf.Failure)
            result.doIfFailure { error ->
                assertEquals(error, errorResponse)
            }
            awaitComplete()
        }
        coVerify { repository.getRemoteInformation() }
        confirmVerified(repository)
    }

    @Test
    fun `execute UseCase returns ResultOf Failure with ErrorType SecurityError`() = coroutinesRule.runTest {
        val errorResponse = ErrorType.SecurityError
        coEvery { repository.getRemoteInformation() } returns ResultOf.Failure(errorResponse)
        useCase.execute(Unit).test {
            val result = awaitItem()
            assert(result is ResultOf.Failure)
            result.doIfFailure { error ->
                assertEquals(error, errorResponse)
            }
            awaitComplete()
        }
        coVerify { repository.getRemoteInformation() }
        confirmVerified(repository)
    }
}