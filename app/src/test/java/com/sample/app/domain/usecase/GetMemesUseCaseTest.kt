package com.sample.app.domain.usecase

import com.nhaarman.mockitokotlin2.*
import com.sample.app.domain.models.ApiError
import com.sample.app.domain.models.Either
import com.sample.app.domain.models.ErrorStatus
import com.sample.app.domain.models.IResponseModel
import com.sample.app.domain.repository.ISampleRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertEquals

@ExperimentalCoroutinesApi
@DisplayName("Validate UseCase tests")
class GetMemesUseCaseTest {

    private lateinit var useCase: GetMemesUseCase

    private val repo by lazy { mock<ISampleRepository>() }

    @BeforeEach
    fun setUp() {
      useCase = GetMemesUseCase(repo)
    }

    @Test
    @DisplayName("Testing GetScooterInfo when Api returns Success")
    fun `should give success on calling the scooter info Api`() {
      val responseData = mock<IResponseModel>()
      val apiSuccessVal = Either.Success(responseData)
      runTest {
        whenever(repo.getMemes()).thenReturn(apiSuccessVal)
        val actualResult = useCase.run(Unit)
        verify(repo, times(1)).getMemes()
        assertEquals(responseData, actualResult.successValue())
        verifyNoMoreInteractions(repo)
      }
    }

    @Test
    @DisplayName("Testing repository returns failure")
    fun `should give failure on calling the scooter info Api & success on getting the data from cache`() {
      val apiFailureVal = Either.Error(ApiError(message = "", errorStatus = ErrorStatus.INTERNAL_SERVER_ERROR))
      runTest {
        whenever(repo.getMemes()).thenReturn(apiFailureVal)
        val actualResult = useCase.run(Unit)
        verify(repo, times(1)).getMemes()
        Assertions.assertTrue(actualResult.errorValue() is ApiError)
        assertEquals(apiFailureVal.errorValue(), actualResult.errorValue())
        verifyNoMoreInteractions(repo)
      }
    }


}
