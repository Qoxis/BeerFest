package ch.hes_so.master.beerfest.utils

import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import ch.hes_so.master.beerfest.adapter.SingleLiveEvent

abstract class BaseViewModel : ViewModel() {

    val navigationCommands = SingleLiveEvent<NavigationCommand>()

    fun navigate(directions: NavDirections) {
        navigationCommands.postValue(NavigationCommand.To(directions))
    }

    fun back(){
        navigationCommands.postValue(NavigationCommand.Back)
    }

    sealed class NavigationCommand {
        data class To(val directions: NavDirections): NavigationCommand()
        object Back: NavigationCommand()
        data class BackTo(val destinationId: Int): NavigationCommand()
        object ToRoot: NavigationCommand()
    }
}