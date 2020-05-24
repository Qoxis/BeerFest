package ch.hes_so.master.beerfest.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ch.hes_so.master.beerfest.R
import ch.hes_so.master.beerfest.models.LanguageModel
import ch.hes_so.master.beerfest.tryDismiss
import kotlinx.android.synthetic.main.fragment_account.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class AccountFragment : Fragment(), KoinComponent {

    val languageModel by inject<LanguageModel>()
    private var viewModel: AccountViewModel? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_account, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        other_language_switcher.setOnClickListener {
            handleLanguageSwitcher()
            return@setOnClickListener
        }
    }


    private fun handleLanguageSwitcher() {
        context?.let { ctx ->
            val builder = AlertDialog.Builder(ctx)

            val locales = mutableListOf<String>()
            val languages = mutableListOf<String>()
            LanguageModel.PRINTABLE_LANG.forEach {
                locales.add(it.key)
                languages.add(getString(it.value))
            }
            builder.setSingleChoiceItems(
                languages.toTypedArray(),
                locales.indexOf(languageModel.language)
            ) { dialogInterface, i ->
                dialogInterface.tryDismiss()
                activity?.let { activity -> languageModel.setLanguage(activity, locales[i]) }
            }
            builder.show()
        }
    }

}
