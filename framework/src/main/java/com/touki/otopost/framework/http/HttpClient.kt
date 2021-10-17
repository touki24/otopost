package com.touki.otopost.framework.http

import com.touki.otopost.common.result.CommonResult
import java.io.InputStream

interface HttpClient {
    /**
     * Composing a Request instance with POST Method
     *
     * @param url [String] the URL API which handles the designated process
     *
     * @return [HttpClient] HTTP Client builder for next chain
     */
    fun post(url : String): HttpClient

    /**
     * Composing a Request instance with GET Method
     *
     * @param url [String] the URL API which handles the designated process
     *
     * @return [HttpClient] HTTP Client builder for next chain
     */
    fun get(url : String): HttpClient

    /**
     * Composing a Request instance with Download
     *
     * @param url [String] the URL API which handles the designated process
     *
     * @return [HttpClient] HTTP Client builder for next chain
     */
    fun download(url: String) : HttpClient

    /**
     * Supplying Request with progress
     *
     * @param action [Unit] the action need to deal with the progress
     *
     * @return [HttpClient] HTTP Client builder for next chain
     */
    fun progress(action: (readBytes: Long, totalBytes: Long) -> Unit) : HttpClient

    /**
     * Supplying Request with bearer authentication token for safer request call
     *
     * @param token [String] the token bearer
     *
     * @return [HttpClient] HTTP Client for next chain
     */
    fun auth(token : String): HttpClient

    /**
     * Supplying Request with object payload
     *
     * @param payload [Any] the payload needed for the call
     *
     * @return [HttpClient] HTTP Client builder for next chain
     */
    fun body(payload : Any): HttpClient

    /**
     * Supplying Request with payload header
     *
     * @param header [String] header type
     * @param value [Any] header value
     *
     * @return [HttpClient] HTTP Client builder for next chain
     *
     */
    fun header(header : String, value : Any): HttpClient

    /**
     * Get request response as [String]
     *
     * @param timeout [Int] set the request time out in millis or leave it to use the default value
     * @param timeoutRead [Int] set the read time out in millis or leave it to use the default value
     *
     * @return [CommonResult] request result parsed as [String]
     */
    fun dispatch(timeout: Int = 10000, timeoutRead: Int = 10000): CommonResult<String>

    /**
     * Get request file response as [InputStream]
     *
     * @param timeout [Int] set the request time out in millis or leave it to use the default value
     * @param timeoutRead [Int] set the read time out in millis or leave it to use the default value
     *
     * @return [CommonResult] request result parsed as [String]
     */
    fun dispatchStream(timeout: Int = 10000, timeoutRead: Int = 10000): CommonResult<InputStream>

    /**
     * Supplying Request with logger
     *
     * @param tag [String] Identifier of the log
     *
     * @return [HttpClient] HTTP Client builder for next chain
     */
    fun log(tag : String): HttpClient
}