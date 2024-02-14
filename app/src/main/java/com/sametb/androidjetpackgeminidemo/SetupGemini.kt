package com.sametb.androidjetpackgeminidemo

import com.google.ai.client.generativeai.GenerativeModel


/*
* Android Jetpack Gemini Demo.com.sametb.androidjetpackgeminidemo
* Created by SAMET BAYAT 
* on 14.02.2024 at 9:06 PM
* Copyright (c) 2024 UNITED WORLD. All rights reserved.
*/


object SetupGemini {
    val generativeModel = GenerativeModel(
        // For text-only input, use the gemini-pro model
        modelName = "gemini-pro",
        // Access your API key as a Build Configuration variable
        apiKey = BuildConfig.API_KEY
    )
}
