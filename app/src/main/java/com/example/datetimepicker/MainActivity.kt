package com.example.datetimepicker

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.datetimepicker.ui.theme.DateTimePickerTheme
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


/*
We want to declare some states that reflect the selected dates and times
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DateTimePickerTheme {
                var pickedDate by remember {
                    mutableStateOf(LocalDate.now())
                }
                var pickedTime by remember {
                    mutableStateOf(LocalTime.NOON)
                }

                val formattedDate by remember {
                    derivedStateOf { //derived from pickedDate state
                         DateTimeFormatter
                             .ofPattern("MM dd yyyy")
                             .format(pickedDate)
                    }
                }

                val formattedTime by remember {
                    derivedStateOf { //derived from pickedDate state
                        DateTimeFormatter
                            .ofPattern("hh:mm")
                            .format(pickedTime)
                    }
                }

                 //material dialog states
                val dateDialogState  = rememberMaterialDialogState()
                val timeDialogState = rememberMaterialDialogState()



                //column that contains buttons that trigger dialogs
                Column(modifier = Modifier
                    .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                    ) {
                    Button(onClick = { //triggers material dialogues
                        dateDialogState.show()
                    }) {
                        Text(text = "Pick date")
                    }
                    Text(text = formattedDate)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { //triggers material dialogues
                        timeDialogState.show()
                    }) {
                        Text(text = "Pick time")
                    }
                    Text(text = formattedTime)
                }

                MaterialDialog(dialogState = dateDialogState,
                               buttons = {
                                   positiveButton(text = "Ok"){
                                       Toast.makeText(applicationContext,
                                               "Clicked ok",
                                               Toast.LENGTH_LONG
                                           ).show()
                                   }
                                   negativeButton(text = "Cancel")
                               }
                    ) { //specify the type of dialog
                          datepicker(
                              initialDate = LocalDate.now(),
                              title = "Pick a date",
                              allowedDateValidator = {
                                  it.dayOfMonth % 2 == 1
                              }
                          ) {
                              pickedDate = it
                          }
                }

                MaterialDialog(dialogState = timeDialogState,
                    buttons = {
                        positiveButton(text = "Ok"){
                            Toast.makeText(applicationContext,
                                "Clicked ok",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        negativeButton(text = "Cancel")
                    }
                ) { //specify the type of dialog
                    timepicker(
                        initialTime = LocalTime.now(),
                        title = "Pick a time",
                        timeRange = LocalTime.MIDNIGHT..LocalTime.NOON
                    ) {
                        pickedTime = it
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DateTimePickerTheme {
        Greeting("Android")
    }
}