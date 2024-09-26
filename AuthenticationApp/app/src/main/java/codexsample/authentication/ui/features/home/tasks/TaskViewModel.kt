package codexsample.authentication.ui.features.home.tasks

import androidx.lifecycle.ViewModel
import codexsample.authentication.ui.common.firebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject
@HiltViewModel
class TaskViewModel @Inject constructor():ViewModel() {
    private val createdAtDateFormat :SimpleDateFormat by lazy {
        SimpleDateFormat("MMM dd yyyy", Locale.getDefault())
    }
    val tasks = loadTasks().flowOn(Dispatchers.Default)
    private fun loadTasks():Flow<List<TaskModel>>{
        return firebaseUser().flatMapLatest { firebaseUser ->
            val userId = firebaseUser?.uid
            if(userId == null){
                // handle user sign out
                flowOf(emptyList())
            }else{
                loadTaskFromFireStoreForUser(userId)
            }
        }
    }
    private fun loadTaskFromFireStoreForUser(userId:String):Flow<List<TaskModel>> = callbackFlow{
        val documentRef = Firebase.firestore.collection("users")
            .document(userId).collection("tasks")
        val snapshotListener =EventListener<QuerySnapshot> {value, error ->
            if(value == null || error != null){
                trySend(emptyList())
            }else{
                trySend(value.documents.mapNotNull {
                    parseTaskFromSnapshot(it)
                })
            }
        }
       val registration =  documentRef.addSnapshotListener(snapshotListener)
        awaitClose{
            //clear any listener
            registration.remove()
        }
    }
    private fun parseTaskFromSnapshot(snapshot:DocumentSnapshot):TaskModel? {
        val taskId = snapshot.id
        val title = snapshot.getString("title") ?: return null
        val description = snapshot.getString("description") ?: "" // default is empty
        val createdAt = snapshot.getTimestamp("createdAt") ?: return null
        val isCompleted = snapshot.getBoolean("isCompleted") ?: false // default is false
       val createdAtDisplay =  createdAtDateFormat.format( createdAt.toDate())
        return TaskModel(taskId,title,description,createdAtDisplay,isCompleted)
    }
}