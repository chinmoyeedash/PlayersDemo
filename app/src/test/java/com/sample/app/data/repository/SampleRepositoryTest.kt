package com.sample.app.data.repository

import com.nhaarman.mockitokotlin2.*
import com.sample.app.data.api.ApiService
import com.sample.app.data.models.ResponseModel
import com.sample.app.domain.models.ApiError
import com.sample.app.domain.repository.ISampleRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import retrofit2.Response


@ExperimentalCoroutinesApi
@DisplayName("Sample Repository test cases")
class SampleRepositoryTest {

  private lateinit var repo: ISampleRepository
  private val service by lazy { mock<ApiService>() }

  @BeforeEach
  fun setUp() {
    repo = SampleRepository(service)
  }

  @Test
  @DisplayName("Testing Data from cloud/api")
  fun `should get data from cloud api successfully`() {
    val cloudData = TestHelper.getMemesData()
    runTest {
      // Assemble or Arrange
      whenever(
        service.getMemes()
      ).thenReturn(Response.success(cloudData))

      // Act
      val actualResult = repo.getMemes()

      // Assert
      assertEquals(cloudData, actualResult.successValue())
      assertNotEquals(cloudData, actualResult.errorValue())

      verify(service, times(1)).getMemes()
      verifyNoMoreInteractions(service)
    }
  }

  @Test
  @DisplayName("Testing Api failure")
  fun `should give error when fetch data from api gets failed`() {
    runTest {
      // Assemble or Arrange
      whenever(service.getMemes())
        .doThrow(TestHelper.getApiError<ResponseModel>())

      // Act
      val actualResult = repo.getMemes()

      // Assert
      Assertions.assertTrue(actualResult.errorValue() is ApiError)
      verify(service, times(1)).getMemes()
      verifyNoMoreInteractions(service)
    }
  }


}

