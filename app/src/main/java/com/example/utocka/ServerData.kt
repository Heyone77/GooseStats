package com.example.utocka

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import okhttp3.Request
import java.io.IOException

@Serializable
data class Role(
    val role: String,
    val count: Int
)

fun parseJson(jsonString: String): List<Role> {
    val json = Json { ignoreUnknownKeys = true }
    val jsonElement = json.parseToJsonElement(jsonString)
    val rolesJsonArray = jsonElement.jsonArray[0].jsonObject["roles"]?.jsonArray
    return rolesJsonArray?.let { json.decodeFromJsonElement(it) } ?: emptyList()
}

fun fetchUserData(username: String): String {
    val url = "http://10.0.2.2:80/api/user-role?username=$username"

    val request = Request.Builder()
        .url(url)
        .build()

    HttpClient.client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")

        return response.body?.string() ?: ""
    }
}
