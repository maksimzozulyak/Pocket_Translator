package com.example.pockettranslator.feature.domain.model

import android.content.ContentValues
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.UnsupportedEncodingException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder

suspend fun translate(word : String) : String {
    var resp: String?
    var url: String?
    var langFrom: String = "en"
    var langTo: String = "ru"
    try {
        url =
            "https://translate.googleapis.com/translate_a/single?" + "client=gtx&" + "sl=" +
                    langFrom + "&tl=" + langTo + "&dt=t&q=" + URLEncoder.encode(
                word,
                "UTF-8"
            )
        val obj = URL(url)
        val con = obj.openConnection() as HttpURLConnection
        con.setRequestProperty("User-Agent", "Mozilla/5.0")
        val `in` = BufferedReader(InputStreamReader(con.inputStream))
        var inputLine: String?
        val response = StringBuffer()
        while (`in`.readLine().also { inputLine = it } != null) {
            response.append(inputLine)
            }
            `in`.close()
            resp = response.toString()
    } catch (e: Exception) {
        return "Connection Error"
    }
    var temp = ""
    try {
        val main = JSONArray(resp)
        val total = main[0] as JSONArray
        for (i in 0 until total.length()) {
            val currentLine = total[i] as JSONArray
            temp = temp + currentLine[0].toString()
        }
        Log.d(ContentValues.TAG, "onPostExecute: $temp")
        if (temp.length > 0) {
            return temp
        } else {
            return "Word Error"
        }
    } catch (e: JSONException) {
        return "Connection Error"
    }
}
