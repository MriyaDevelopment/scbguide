package com.spravochnic.scbguide.di.annotations

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HttpRequestInterceptor