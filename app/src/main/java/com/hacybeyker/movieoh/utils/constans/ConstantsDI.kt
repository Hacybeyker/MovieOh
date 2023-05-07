package com.hacybeyker.movieoh.utils.constans

object ConstantsDI {
    object Named {
        const val IDENTIFIER_TM_DB = "TMDB"
        const val BASE_URL = "base_url"
        const val API_KEY = "api_key"

        const val IDENTIFIER_PLATFORMS = "PLATFORMS"
        const val BASE_URL_PLATFORMS = "base_url_platforms"
    }

    object Parameters {
        const val TIMEOUT = 60L
        const val CONTENT_LENGTH = 250_000L
        const val AUTH_TOKEN = "Auth-Token"
        const val BEARER = "Bearer"
    }

    object Http {
        const val RESPONSE_OK = 200
    }
}
