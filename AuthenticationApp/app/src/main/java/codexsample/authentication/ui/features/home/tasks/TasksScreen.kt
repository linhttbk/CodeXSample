package codexsample.authentication.ui.features.home.tasks

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import codexsample.authentication.R
import codexsample.authentication.ui.theme.checkBoxBackgroundColor
import codexsample.authentication.ui.theme.checkBoxBorderColor
import codexsample.authentication.ui.theme.redTintColor

@Composable
fun TasksScreen(taskViewModel: TaskViewModel = hiltViewModel()) {
    val tasks by taskViewModel.tasks.collectAsState(emptyList())
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(tasks) { task ->
            TaskItem(task.title, task.description, task.createdAt, task.isCompleted)
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
                color = MaterialTheme.colorScheme.onPrimary
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