package pe.ralvaro.movietek.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {

    private val launchViewModel: LauncherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        screenSplash.setKeepOnScreenCondition { true }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launchViewModel.launchDestination.collect {
                    when (it) {
                        InitialView.Main -> {
                            startActivity(
                                Intent(this@LauncherActivity, MainActivity::class.java).addFlags()
                            )
                        }

                        InitialView.Login -> {
                            startActivity(
                                Intent(this@LauncherActivity, LoginActivity::class.java).addFlags()
                            )
                        }
                    }
                    finish()
                }
            }
        }
    }

}

fun Intent.addFlags(): Intent {
    this.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    return this
}