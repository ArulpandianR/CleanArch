package com.t2s.task.helper

import android.content.Context
import android.net.ConnectivityManager
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Toast

object TaskHelper {

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        val isConnected = !(activeNetworkInfo == null || !activeNetworkInfo.isConnected)
        if (!isConnected) {
            Toast.makeText(context, "Please check your network connection", Toast.LENGTH_SHORT)
                .show()
        }
        return isConnected
    }


    fun changeTextColor(
        firstString: String?,
        lastString: String, colorCode: Int
    ): Spannable {
        val changeString = firstString ?: ""
        val totalString = changeString + lastString
        val firstStringSize = changeString.length
        val totaltStringSize = totalString.length
        val spanText = SpannableString(totalString)
        spanText.setSpan(ForegroundColorSpan(colorCode), firstStringSize, totaltStringSize, 0)
        return spanText
    }
}