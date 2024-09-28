package codexsample.authentication.ui.features.home.tasks

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import codexsample.authentication.R
import codexsample.authentication.ui.theme.checkBoxBackgroundColor
import codexsample.authentication.ui.theme.checkBoxBorderColor
import codexsample.authentication.ui.theme.redTintColor
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(taskViewModel: TaskViewModel = hiltViewModel()) {
    val tasks by taskViewModel.tasks.collectAsState(emptyList())
    var shouldShowAddTaskSheet by remember {
        mutableStateOf(false)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(tasks) { task ->
                TaskItem(task.title, task.description, task.createdAt, task.isCompleted)
            }
        }
        FloatingActionButton(
            onClick = {
                shouldShowAddTaskSheet = true
            },
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.BottomEnd),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        if (shouldShowAddTaskSheet) {
            ModalBottomSheet(onDismissRequest = {
                shouldShowAddTaskSheet = false
            }, containerColor = Color.White) {
                AddTaskSheet(onSave = { title, description, createdDate ->
                    //taskViewModel.addTask(title, description)
                    taskViewModel.addTask(title, description, createdDate)
                    shouldShowAddTaskSheet = false
                }, onCancel = {
                    shouldShowAddTaskSheet = false
                })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskSheet(onSave: (String, String, Calendar) -> Unit, onCancel: () -> Unit) {
    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    val buttonTextEnabled by remember {
        derivedStateOf {
            title.isNotEmpty() && description.isNotEmpty()
        }
    }
    val formatDisplayDate = remember {
        SimpleDateFormat("MMM, dd yyyy HH:mm", Locale.getDefault())
    }
    var currentSelectedCreateDate by remember {
        mutableStateOf(System.currentTimeMillis())
    }
    var selectedTime by remember {
        mutableStateOf(Calendar.getInstance())
    }
    val displayDate by remember {
        derivedStateOf {
            val date = Calendar.getInstance().apply {
                timeInMillis = currentSelectedCreateDate
                set(Calendar.HOUR_OF_DAY,selectedTime.get(Calendar.HOUR_OF_DAY))
                set(Calendar.MINUTE,selectedTime.get(Calendar.MINUTE))
                set(Calendar.SECOND,selectedTime.get(Calendar.SECOND))
            }.time
            formatDisplayDate.format(date)
        }
    }
    var shouldShowDatePicker by remember {
        mutableStateOf(false)
    }
    var shouldShowTimePicker by remember {
        mutableStateOf(false)
    }
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = currentSelectedCreateDate)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Text(
            "Add task",
            style = MaterialTheme.typography.bodySmall,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(
            Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(MaterialTheme.colorScheme.secondary)
        )
        OutlinedTextField(value = title,
            onValueChange = {
                title = it
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                unfocusedBorderColor = Color.White,
                focusedBorderColor = Color.White,
            ),
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            placeholder = {
                Text(
                    text = stringResource(R.string.place_holder_title),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black.copy(alpha = 0.3f),
                    ),
                )
            })
        OutlinedTextField(value = description,
            onValueChange = {
                description = it
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                unfocusedBorderColor = Color.White,
                focusedBorderColor = Color.White,
            ),
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            placeholder = {
                Text(
                    text = stringResource(R.string.place_holder_description),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black.copy(alpha = 0.3f),
                    ),
                )
            })
        Row(Modifier.fillMaxWidth().clickable {
            shouldShowDatePicker = true
        }, verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.ic_calendar),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Black),
                modifier = Modifier.padding(start = 24.dp, end = 5.dp)
            )
            Text(
                text = displayDate,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                ),
            )
        }
        Spacer(
            Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(MaterialTheme.colorScheme.secondary)
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = onCancel,
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 12.dp)
            ) {
                Text("Cancel")
            }
            Spacer(Modifier.weight(1.0f))
            Button(
                onClick = {
                    val selectedDate = Calendar.getInstance().apply {
                        timeInMillis = currentSelectedCreateDate
                        set(Calendar.HOUR_OF_DAY,selectedTime.get(Calendar.HOUR_OF_DAY))
                        set(Calendar.MINUTE,selectedTime.get(Calendar.MINUTE))
                        set(Calendar.SECOND,0)
                    }
                    onSave.invoke(title, description, selectedDate)
                }, enabled = buttonTextEnabled, colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.secondary
                ), modifier = Modifier.padding(vertical = 5.dp, horizontal = 12.dp)
            ) {
                Text("Save", color = Color.White)
            }
        }
    }
    if(shouldShowDatePicker){
        DatePickerDialog(
            onDismissRequest = {
                shouldShowDatePicker = false
            },
            confirmButton ={
                TextButton(onClick = {
                    if(datePickerState.selectedDateMillis != null){
                        currentSelectedCreateDate = datePickerState.selectedDateMillis!!
                    }
                    shouldShowDatePicker = false
                    shouldShowTimePicker = true
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    shouldShowDatePicker = false
                }) {
                    Text("Dismiss")
                }
            },
        ) {
            DatePicker(datePickerState)
        }
    }
    val timePickerState  = rememberTimePickerState(
        initialMinute = selectedTime.get(Calendar.MINUTE),
        initialHour = selectedTime.get(Calendar.HOUR_OF_DAY),
        is24Hour = true
    )
    if(shouldShowTimePicker){
        Dialog(onDismissRequest ={
            shouldShowTimePicker = false
        } ,) {
            Column (modifier =Modifier.background(Color.White)){
                TimePicker(
                    state = timePickerState,
                    modifier =Modifier.padding(top = 5.dp).align(Alignment.CenterHorizontally)
                )
                Row(modifier =Modifier.padding(horizontal = 5.dp, vertical = 5.dp)) {
                    Button(onClick = {
                        shouldShowTimePicker= false
                    }) {
                        Text("Dismiss")
                    }
                    Spacer(Modifier.weight(1f))
                    Button(onClick = {
                        shouldShowTimePicker= false
                        selectedTime = Calendar.getInstance().apply {
                            set(Calendar.HOUR_OF_DAY,timePickerState.hour)
                            set(Calendar.MINUTE,timePickerState.minute)
                        }
                    }) {
                        Text("Confirm")
                    }
                }
            }
        }

    }
}

@Composable
fun TaskItem(title: String, description: String, createdDate: String, isCompleted: Boolean) {
    Row(Modifier.fillMaxWidth()) {
        Box(
            Modifier
                .padding(start = 12.dp, top = 22.dp, end = 20.dp)
                .size(16.dp)
                .background(checkBoxBackgroundColor, CircleShape)
                .border(
                    width = 1.dp, checkBoxBorderColor, CircleShape
                )
        )
        Column(
            modifier = Modifier
                .padding(top = 22.dp)
                .weight(1f)
        ) {
            Text(
                title,
                style = MaterialTheme.typography.bodySmall,
                fontSize = 18.sp,
                color = Color.Black
            )
            Text(
                description,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(top = 2.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 6.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_calendar),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(redTintColor)
                )
                Text(
                    createdDate,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 10.sp,
                    color = redTintColor,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }
            Spacer(
                Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.secondary)
            )
        }
    }
}