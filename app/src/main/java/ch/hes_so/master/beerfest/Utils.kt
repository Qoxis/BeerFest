package ch.hes_so.master.beerfest

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.Menu
import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import timber.log.Timber
import java.lang.ref.WeakReference

fun NavController.navigateBottom(
    resId: Int,
    source: Int,
    bottom_bar: BottomNavigationView,
    order: Int
) {
    try {
        if (currentDestination?.id != source) {

            val builder = NavOptions.Builder()
                .setLaunchSingleTop(true)
            if (order and Menu.CATEGORY_SECONDARY == 0) {
                builder.setPopUpTo(R.id.fragment_params, false)
            }
            navigate(resId, null, builder.build())

            val navController = this
            val weakReference = WeakReference(bottom_bar)
            this.addOnDestinationChangedListener(
                object : NavController.OnDestinationChangedListener {
                    override fun onDestinationChanged(
                        controller: NavController,
                        destination: NavDestination, arguments: Bundle?
                    ) {
                        val view = weakReference.get()
                        if (view == null) {
                            navController.removeOnDestinationChangedListener(this)
                            return
                        }
                        val menu = view.menu
                        var h = 0
                        val size = menu.size()
                        while (h < size) {
                            val item = menu.getItem(h)
                            if (matchDestination(destination, item.itemId)) {
                                item.isChecked = true
                            }
                            h++
                        }
                    }
                })
        }
    } catch (e: Exception) {
        Timber.e(e, "Navigation failed !")
    }
}

inline fun Handler.postIfNotIn(crossinline message: () -> Unit) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        message()
    } else {
        post {
            message()
        }
    }
}

fun DialogInterface?.tryDismiss() {
    this ?: return

    uiHandler.postIfNotIn {
        try {
            if (this is Dialog) {
                if (isShowing) {
                    dismiss()
                }
            } else {
                dismiss()
            }
        } catch (ex: Exception) {
            Timber.e(ex)
        }
    }
}
val uiHandler by lazy { Handler(Looper.getMainLooper()) }

fun ConstraintLayout.changeConstraints(changes: ConstraintSet.() -> Unit): ConstraintSet {
    return ConstraintSet().apply {
        clone(this@changeConstraints)
        changes(this)
        applyTo(this@changeConstraints)
    }
}

fun Number.fromDpToPx(context: Context): Int {
    val r = context.resources
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), r.displayMetrics)
        .toInt()
}

internal fun matchDestination(
    destination: NavDestination,
    @IdRes destId: Int
): Boolean {
    var currentDestination: NavDestination? = destination
    while (currentDestination!!.id != destId && currentDestination.parent != null) {
        currentDestination = currentDestination.parent
    }
    return currentDestination.id == destId
}

inline fun SharedPreferences.editAndApply(crossinline block: SharedPreferences.Editor.() -> Unit) {
    edit().apply {
        block()
        apply()
    }
}
