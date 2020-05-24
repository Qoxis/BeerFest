package ch.hes_so.master.beerfest

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import ch.hes_so.master.beerfest.models.LanguageModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.get
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nav_view.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.fragment_blog -> nav_host_fragment.findNavController().navigateBottom(
                    R.id.action_bottombar_blog,
                    R.id.fragment_blog,
                    nav_view,
                    it.order
                )
                R.id.fragment_scan -> nav_host_fragment.findNavController().navigateBottom(
                    R.id.action_bottombar_scan,
                    R.id.fragment_scan,
                    nav_view,
                    it.order
                )
                R.id.fragment_account -> nav_host_fragment.findNavController().navigateBottom(
                    R.id.action_bottombar_account,
                    R.id.fragment_account,
                    nav_view,
                    it.order
                    )
                R.id.fragment_brewery -> nav_host_fragment.findNavController().navigateBottom(
                    R.id.action_bottombar_brewery,
                    R.id.fragment_brewery,
                    nav_view,
                    it.order
                )
            }
            true
        }
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(updateBaseContextLocale(newBase))
    }

    private fun updateBaseContextLocale(context: Context): Context {
        val language = get<LanguageModel>().language
        val locale = Locale(language)
        Locale.setDefault(locale)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResourcesLocale(context, locale)
        }

        return updateResourcesLocaleLegacy(context, locale)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun updateResourcesLocale(context: Context, locale: Locale): Context {
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLocaleLegacy(context: Context, locale: Locale): Context {
        val resources = context.resources
        val configuration = resources.configuration
        @Suppress("DEPRECATION")
        configuration.locale = locale
        @Suppress("DEPRECATION")
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }
}
