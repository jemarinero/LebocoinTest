package com.lebocoin.data.datasource

import com.lebocoin.data.database.dao.InformationDao
import com.lebocoin.data.database.entity.InformationEntity
import com.lebocoin.libs.MainCoroutinesRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class InformationLocalDSTest {

    @get:Rule
    val coroutinesRule = MainCoroutinesRule()

    private val mockkDao: InformationDao = mockk()

    private lateinit var dataSource: InformationLocalDS

    @Before
    fun setup() {
        dataSource = InformationLocalDS(mockkDao)
    }

    @Test
    fun `call insertOrUpdate returns number of rows affected`() = coroutinesRule.runTest {
        coEvery { mockkDao.insertOrUpdate(any()) } returns 1L
        val result = dataSource.insertOrUpdate(mockk())
        assert(result == 1L)
        coVerify { mockkDao.insertOrUpdate(any()) }
        confirmVerified(mockkDao)
    }

    @Test
    fun `call getAll returns list of items`() = coroutinesRule.runTest {
        every { mockkDao.getAll(any(), any()) } returns listOf(InformationEntity(0, 0, "", "", ""))
        val result = dataSource.getAll(1, 1)
        assertTrue(result.isNotEmpty())
        coVerify { mockkDao.getAll(any(), any()) }
        confirmVerified(mockkDao)
    }

    @Test
    fun `call getAll returns empty list`() = coroutinesRule.runTest {
        every { mockkDao.getAll(any(), any()) } returns emptyList()
        val result = dataSource.getAll(1, 1)
        assertTrue(result.isEmpty())
        coVerify { mockkDao.getAll(any(), any()) }
        confirmVerified(mockkDao)
    }
}