package co.linhtt.githubuser.ui.users

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import co.linhtt.domain.model.GithubUser
import coil.compose.AsyncImage

// we use paging here
@Composable
fun UserListScreen(users: LazyPagingItems<GithubUser>,onItemClicked :(GithubUser)->Unit ) {
    // systemBarsPadding to control status bar padding and navigation bar padding
    LazyColumn(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // show loading state on loading the first page
        if (users.loadState.refresh == LoadState.Loading) {
            item {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
        items(count = users.itemCount, key = users.itemKey {
            // unique user
            it.login
        }) { index ->
            val user = users[index]
            if (user != null) {
                UserCard(user, onCardClicked = {
                    onItemClicked.invoke(user)
                })
            }
        }
    }

}

@Composable
private fun UserCard(user: GithubUser,onCardClicked :() ->Unit ) {
    Card(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 5.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        onClick = onCardClicked
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            // user avatar
            AsyncImage(
                model = user.avatarUrl,
                contentDescription = null,
                modifier = Modifier
                    .padding(20.dp)
                    .clip(
                        CircleShape
                    )
                    .size(90.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = user.login,
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp),
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                )
                Text(
                    text = user.htmlUrl,
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 12.sp),
                    color = Color(0xFF007AFF),
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth(),
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
}
