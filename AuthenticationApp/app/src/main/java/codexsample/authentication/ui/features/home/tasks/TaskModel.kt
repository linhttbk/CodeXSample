package codexsample.authentication.ui.features.home.tasks

data class TaskModel(
    val id: String,
    val title: String,
    val description: String,
    val createdAt: String,
    val isCompleted: Boolean
)