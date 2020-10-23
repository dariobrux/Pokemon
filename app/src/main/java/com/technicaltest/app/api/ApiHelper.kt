package com.technicaltest.app.api

import com.technicaltest.app.other.Resource
import retrofit2.Response
import javax.inject.Inject

/**
 *
 * Created by Dario Bruzzese on 22/10/2020.
 *
 * This class get the results from the api service and map the result object
 * to an useful object with the status.
 *
 */

class ApiHelper @Inject constructor(private val apiService: ApiService) {

    suspend fun getPokemon(offset: Int, limit: Int) = getResult { apiService.pokemon(offset, limit) }

    /**
     * Map the result of the [call] to a [Resource] object.
     * @param call the api service function to invoke and map the result.
     * @return the [Resource] object with the the response as content.
     */
    private suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    /**
     * Map a message as a [Resource] error state.
     * @param message the message to convert.
     * @return the [Resource] object mapped with error status.
     */
    private fun <T> error(message: String): Resource<T> {
        return Resource.error("Network call has failed for a following reason: $message")
    }
}