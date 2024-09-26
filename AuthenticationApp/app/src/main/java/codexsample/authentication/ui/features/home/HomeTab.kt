package codexsample.authentication.ui.features.home

import codexsample.authentication.R

enum class HomeTab(val titleResId:Int, val iconResId:Int){
    Tasks(R.string.tasks_tab_title,R.drawable.ic_tab_task),
    Statistics(R.string.statistics_tab_title,R.drawable.ic_statistic_tab),
    Progress(R.string.progress_tab_title,R.drawable.ic_progress_tab),
    Chat(R.string.chat_tab_title,R.drawable.ic_chat_tab),
    Settings(R.string.settings_tab_title,R.drawable.ic_settings_tab),

}