package com.sametb.androidjetpackgeminidemo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            val scope = rememberCoroutineScope()
            var text by remember { mutableStateOf("Ask anything..!") }
            var question by remember { mutableStateOf("") }

            // TODO: Keyboard configuration
            val keyboardController = LocalSoftwareKeyboardController.current
            val focusRequester = remember { FocusRequester() }



            /*
            LaunchedEffect(Unit) {
                var response = ""
                scope.launch {
                    response = withContext(Dispatchers.IO) {
                        SetupGemini.generativeModel.generateContent(prompt = question)
                    }.text.toString()
                    Log.d("MainActivity", "Gemini response: $response")
                }
                text = response
            }
            */

            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
                ,
//                color = MaterialTheme.colorScheme.background
            ) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Row {
                        BasicTextField2(
                            value = question,
                            onValueChange = { question = it },
                            modifier = Modifier
                                .weight(10f)
                                .focusRequester(focusRequester)
                            ,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    keyboardController?.hide()

                                    if (question.isEmpty()) {
                                        Toast.makeText(
                                            context,
                                            "Please enter a question",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        return@KeyboardActions
                                    }

                                    Toast.makeText(context, "Wait for Gemini AI response", Toast.LENGTH_SHORT).show()
                                    scope.launch {
                                        val response = withContext(Dispatchers.IO) {
                                            SetupGemini.generativeModel.generateContent(prompt = question)
                                        }
                                        text = response.text.toString()
                                        Log.d("MainActivity", "Gemini response: ${response.text}")
                                    }
                                }
                            ),
                        )
                        Button(
                            modifier = Modifier.weight(3f),
                            onClick = {
                                if (question.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        "Please enter a question",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    return@Button
                                }

                                Toast.makeText(context, "Wait for Gemini AI response", Toast.LENGTH_SHORT).show()
                                scope.launch {
                                    val response = withContext(Dispatchers.IO) {
                                        SetupGemini.generativeModel.generateContent(prompt = question)
                                    }
                                    text = response.text.toString()
                                    Log.d("MainActivity", "Gemini response: ${response.text}")
                                }
                            }
                        ) {
                            Text(text = "Ask")
                        }
                    }
                    Spacer(modifier = Modifier.padding(12.dp))
                    Text(
                        text = text,
                        modifier = Modifier
                            .weight(7f)
                            .verticalScroll(rememberScrollState())
                    )
                }
            }
        }
    }
}
