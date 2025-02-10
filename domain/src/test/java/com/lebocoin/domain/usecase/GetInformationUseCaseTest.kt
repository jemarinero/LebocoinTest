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
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetInformationUseCaseTest {

    @get:Rule
    val coroutinesRule = MainCoroutinesRule()

    private val repository: InformationRepository = mockk()

    private lateinit var useCase: GetInformationUseCase

    @Before
    fun setUp() {
        useCase = GetInformationUseCase(repository, coroutinesRule.testDispatcherProvider)
    }

    @Test
    fun `execute UseCase returns ResultOf Success with a list of items`() = coroutinesRule.runTest {
        coEvery { repository.getInformation(any(), any()) } returns ResultOf.Success(listOf(
            Information(0,0,"","","")
        ))
        useCase.execute(GetInformationUseCase.Pagination(1, 1)).test {
            val result = awaitItem()
            assert(result is ResultOf.Success)
            result.doIfSuccess {
                assertTrue(it.isNotEmpty())
            }
            awaitComplete()
        }
        coVerify { repository.getInformation(any(), any()) }
        confirmVerified(repository)
    }

    @Test
    fun `execute UseCase returns ResultOf Failure with ErrorType EmptyError`() = coroutinesRule.runTest {
        val errorResponse = ErrorType.EmptyError
        coEvery { repository.getInformation(any(), any()) } returns ResultOf.Failure(errorResponse)
        useCase.execute(GetInformationUseCase.Pagination(1, 1)).test {
            val result = awaitItem()
            assert(result is ResultOf.Failure)
            result.doIfFailure { error ->
                assertEquals(error, errorResponse)
            }
            awaitComplete()
        }
        coVerify { repository.getInformation(any(), any()) }
        confirmVerified(repository)
    }

    @Test
    fun `execute UseCase returns ResultOf Failure with ErrorType ConnectivityError`() = coroutinesRule.runTest {
        val errorResponse = ErrorType.ConnectivityError
        coEvery { repository.getInformation(any(), any()) } returns ResultOf.Failure(errorResponse)
        useCase.execute(GetInformationUseCase.Pagination(1, 1)).test {
            val result = awaitItem()
            assert(result is ResultOf.Failure)
            result.doIfFailure { error ->
                assertEquals(error, errorResponse)
            }
            awaitComplete()
        }
        coVerify { repository.getInformation(any(), any()) }
        confirmVerified(repository)
    }

    @Test
    fun `execute UseCase returns ResultOf Failure with ErrorType UnknownError`() = coroutinesRule.runTest {
        val errorResponse = ErrorType.UnknownError
        coEvery { repository.getInformation(any(), any()) } returns ResultOf.Failure(errorResponse)
        useCase.execute(GetInformationUseCase.Pagination(1, 1)).test {
            val result = awaitItem()
            assert(result is ResultOf.Failure)
            result.doIfFailure { error ->
                assertEquals(error, errorResponse)
            }
            awaitComplete()
        }
        coVerify { repository.getInformation(any(), any()) }
        confirmVerified(repository)
    }

    @Test
    fun `execute UseCase returns ResultOf Failure with ErrorType ServiceError`() = coroutinesRule.runTest {
        val errorResponse = ErrorType.ServiceError
        coEvery { repository.getInformation(any(), any()) } returns ResultOf.Failure(errorResponse)
        useCase.execute(GetInformationUseCase.Pagination(1, 1)).test {
            val result = awaitItem()
            assert(result is ResultOf.Failure)
            result.doIfFailure { error ->
                assertEquals(error, errorResponse)
            }
            awaitComplete()
        }
        coVerify { repository.getInformation(any(), any()) }
        confirmVerified(repository)
    }

    @Test
    fun `execute UseCase returns ResultOf Failure with ErrorType ServerError`() = coroutinesRule.runTest {
        val errorResponse = ErrorType.ServerError
        coEvery { repository.getInformation(any(), any()) } returns ResultOf.Failure(errorResponse)
        useCase.execute(GetInformationUseCase.Pagination(1, 1)).test {
            val result = awaitItem()
            assert(result is ResultOf.Failure)
            result.doIfFailure { error ->
                assertEquals(error, errorResponse)
            }
            awaitComplete()
        }
        coVerify { repository.getInformation(any(), any()) }
        confirmVerified(repository)
    }

    @Test
    fun `execute UseCase returns ResultOf Failure with ErrorType SecurityError`() = coroutinesRule.runTest {
        val errorResponse = ErrorType.SecurityError
        coEvery { repository.getInformation(any(), any()) } returns ResultOf.Failure(errorResponse)
        useCase.execute(GetInformationUseCase.Pagination(1, 1)).test {
            val result = awaitItem()
            assert(result is ResultOf.Failure)
            result.doIfFailure { error ->
                assertEquals(error, errorResponse)
            }
            awaitComplete()
        }
        coVerify { repository.getInformation(any(), any()) }
        confirmVerified(repository)
    }
}