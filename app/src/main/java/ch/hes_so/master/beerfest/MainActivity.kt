package ch.hes_so.master.beerfest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)

        //navView.setupWithNavController(navController)
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
                /*R.id.loginFragment -> {
                    if (userModel.isQonnected) {
                        navHost.findNavController()
                            .navigateBottom(R.id.action_bottombar_account, R.id.accountFragment, bottom_bar,  it.order)
                    } else {
                        navHost.findNavController()
                            .navigateBottom(R.id.action_bottombar_login, R.id.loginFragment, bottom_bar,  it.order)

                    }
                }*/
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
}
