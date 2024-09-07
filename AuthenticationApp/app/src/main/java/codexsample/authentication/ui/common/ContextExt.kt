package codexsample.authentication.ui.common

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openURL(url:String){
    kotlin.runCatching {
        Uri.parse(url).run {
            startActivity(Intent().apply {
                action = Intent.ACTION_VIEW
                data = this@run
            })
        }
    }.onFailure {
        it.printStackTrace()
    }
}