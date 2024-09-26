package codexsample.authentication.ui.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import codexsample.authentication.ui.features.home.tasks.TasksScreen

@Composable
fun HomeScreen(selectedTab:HomeTab,onTabSelected :(HomeTab)->Unit) {
    Scaffold(bottomBar = {
        NavigationBar {
            HomeTab.entries.map { tab ->
                NavigationBarItem(selectedTab == tab, onClick = {
                    onTabSelected.invoke(tab)
                }, icon = {
                    Icon(painter = painterResource(tab.iconResId), contentDescription = null)
                }, label = {
                    Text(text = stringResource(tab.titleResId))
                })
            }
        }
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when(selectedTab){
                HomeTab.Tasks -> {
                    TasksScreen()
                }
                HomeTab.Statistics ->{
                    Text("Statistic Screen")
                }
                HomeTab.Progress -> {
                    Text("Progress Screen")
                }
                HomeTab.Chat -> {
                    Text("Chat Screen")
                }
                HomeTab.Settings -> {
                    Text("Settings Screen")
                }
            }
        }
    }
}