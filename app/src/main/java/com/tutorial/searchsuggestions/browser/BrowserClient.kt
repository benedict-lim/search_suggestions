package com.tutorial.searchsuggestions.browser

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import com.tutorial.searchsuggestions.R

interface BrowserClient {
    /**
     * Open search results in designated search engine (Google)
     *
     * @param searchTerm Confirmed input of search term
     */
    fun openSearchResults(searchTerm: String)

    /**
     * Open link in a Custom Tab, or external browser if not available
     *
     * @param uri Uri of link
     */
    fun launchUrl(uri: Uri)
}

class BrowserClientImpl(private val context: Context) : BrowserClient {

    override fun openSearchResults(searchTerm: String) {
        val url = Uri.parse(SEARCH_URL)
            .buildUpon()
            .appendQueryParameter(SEARCH_QUERY_PARAM, searchTerm)
            .build()

        launchUrl(url)
    }

    override fun launchUrl(uri: Uri) {
        val intent = CustomTabsIntent.Builder()
            .setShowTitle(true)
            .setCloseButtonIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    android.R.drawable.ic_menu_close_clear_cancel
                )
            )
            .build()

        try {
            intent.launchUrl(context, uri)
        } catch (e: ActivityNotFoundException) {
            openBrowser(uri)
        }
    }

    private fun openBrowser(uri: Uri) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                context,
                context.getString(R.string.error_open_browser),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private companion object {
        const val SEARCH_URL = "https://www.google.com/search"
        const val SEARCH_QUERY_PARAM = "q"
    }
}
