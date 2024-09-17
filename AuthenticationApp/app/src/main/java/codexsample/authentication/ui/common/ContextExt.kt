package codexsample.authentication.ui.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings.Global.getString
import codexsample.authentication.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

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

fun Context.createGoogleSignInClient():GoogleSignInClient{
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.default_web_client_id))
        .requestEmail()
        .build()
    return GoogleSignIn.getClient(this,gso)
}