package br.com.carlosscotus.npbrasil.framework.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class HeadersInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        chain.request()
            .headers
            .newBuilder()
            .add("", "")
            .build()


        return chain.proceed(chain.request())
    }
}