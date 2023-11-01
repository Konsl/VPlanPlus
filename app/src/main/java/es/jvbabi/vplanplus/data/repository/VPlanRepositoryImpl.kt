package es.jvbabi.vplanplus.data.repository

import android.util.Log
import es.jvbabi.vplanplus.domain.DataResponse
import es.jvbabi.vplanplus.domain.model.School
import es.jvbabi.vplanplus.domain.model.xml.VPlanData
import es.jvbabi.vplanplus.domain.repository.VPlanRepository
import es.jvbabi.vplanplus.domain.usecase.Response
import io.ktor.client.HttpClient
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.request.basicAuth
import io.ktor.client.request.request
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod.Companion.Get
import java.net.UnknownHostException
import java.time.LocalDate

class VPlanRepositoryImpl : VPlanRepository {
    override suspend fun getVPlanData(school: School, date: LocalDate): DataResponse<VPlanData?> {
        return try {
            val response = HttpClient {
                install(HttpTimeout) {
                    requestTimeoutMillis = 5000
                    connectTimeoutMillis = 5000
                    socketTimeoutMillis = 5000
                }
            }.request("https://www.stundenplan24.de/${school.id}/wplan/wdatenk/WPlanKl_${date.year}${date.monthValue}${date.dayOfMonth.toString().padStart(2, '0')}.xml") {
                method = Get
                basicAuth(school.username, school.password)
            }
            DataResponse(VPlanData(response.bodyAsText(), school.id!!), Response.SUCCESS)
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException, is ConnectTimeoutException, is HttpRequestTimeoutException -> DataResponse(null, Response.NO_INTERNET)
                else -> {
                    Log.d(this.javaClass.name, "other error: ${e.javaClass.name} ${e.stackTraceToString()}")
                    DataResponse(null, Response.OTHER)
                }
            }
        }
    }
}