package com.example.utocka

import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException


object HttpClient {
    val client = OkHttpClient()
}

const val URL = "http://10.0.2.2/api/user-role"
const val MEDIA_TYPE_JSON = "application/json"

fun sendRolesUpdate(client: OkHttpClient, username: String, role: String) {
    val json = """
        {
            "username": "$username",
            "role": "$role"
        }
    """.trimIndent()

    val requestBody = json.toRequestBody(MEDIA_TYPE_JSON.toMediaType())

    val request = Request.Builder()
        .url(URL)
        .post(requestBody)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            // Обработка ошибки
            Log.i("info", "Request failed: $e")
        }

        override fun onResponse(call: Call, response: Response) {
            response.use { resp ->
                val responseBody = resp.body?.string()
                Log.i("info", "Response: $responseBody")
            }
        }
    })
}


