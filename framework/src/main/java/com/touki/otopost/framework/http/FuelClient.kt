package com.touki.otopost.framework.http

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.core.requests.download
import com.github.kittinunf.fuel.gson.jsonBody
import com.touki.otopost.common.result.CommonResult
import com.touki.otopost.common.result.HttpError
import java.io.InputStream

internal class FuelClient: HttpClient {
    private lateinit var requestClient : Request

    override fun post(url : String) = apply {
        requestClient = Fuel.post(url)
    }

    override fun get(url : String) = apply {
        requestClient = Fuel.get(url)
    }

    override fun delete(url: String) = apply {
        requestClient = Fuel.delete(url)
    }

    override fun put(url: String) = apply {
        requestClient = Fuel.put(url)
    }

    override fun download(url: String) = apply {
        requestClient = Fuel.download(url)
    }

    override fun progress(action: (readBytes: Long, totalBytes: Long) -> Unit) = apply {
        requestClient.download().progress { _readBytes, _totalBytes ->
            action(_readBytes, _totalBytes)
        }
    }

    override fun auth(token : String) = apply {
        requestClient.authentication().bearer(token)
    }

    override fun body(payload : Any) = apply {
        requestClient.jsonBody(payload)
    }

    override fun header(header : String, value : Any) = apply {
        requestClient.header(header, value)
    }

    override fun dispatch(timeout: Int, timeoutRead: Int): CommonResult<String> {
        return try{
            requestClient.timeout(timeout).timeoutRead(timeoutRead)
                .responseString().third.fold(
                    success = { json ->
                        Log.d("TAG", "dispatch success: $json")
                        CommonResult.Success(json)
                    },
                    failure = { error ->
                        Log.e("TAG", "dispatch failure: ${error.localizedMessage.orEmpty()}")
                        val message = if (error.localizedMessage.orEmpty().contains("Unable to resolve host")) {
                            "Can't connect to server, please check your internet connection"
                        } else {
                            error.localizedMessage.orEmpty()
                        }
                        CommonResult.Failure(HttpError(message))
                    }
                )
        } catch (e: Exception){
            e.printStackTrace()
            CommonResult.Failure(HttpError("Exception on dispatch"))
        }

    }

    override fun dispatchStream(timeout: Int, timeoutRead: Int): CommonResult<InputStream> {
        return try {
            val stream = requestClient.timeout(timeout).timeoutRead(timeoutRead)
                .response()
                .second.body().toStream()
            CommonResult.Success(stream)
        } catch (e: Exception) {
            e.printStackTrace()
            CommonResult.Failure(HttpError("Exception on dispatch"))
        }
    }

    override fun log(tag : String) = apply{
        requestClient.also {
            Log.d(tag, "$it")
        }
    }
}