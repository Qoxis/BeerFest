package ch.hes_so.master.beerfest

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import ch.hes_so.master.beerfest.models.ConfigModel
import ch.hes_so.master.beerfest.models.LanguageModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import java.util.*


class MainActivity : AppCompatActivity() {

    private val configModel by inject<ConfigModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configModel.setLastBreweryId(-1)

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
                R.id.fragment_params -> nav_host_fragment.findNavController().navigateBottom(
                    R.id.action_bottombar_params,
                    R.id.fragment_params,
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
        nav_view.menu.findItem(R.id.fragment_scan).isChecked = true
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
