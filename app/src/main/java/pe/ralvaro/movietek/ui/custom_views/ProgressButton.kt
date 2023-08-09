package pe.ralvaro.movietek.ui.custom_views

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.progressindicator.CircularProgressIndicator
import pe.ralvaro.movietek.R
import pe.ralvaro.movietek.utils.gone
import pe.ralvaro.movietek.utils.visible

class ProgressButton(
    context: Context,
    attrs: AttributeSet? = null
): ConstraintLayout(context, attrs) {


    private var progressBar: CircularProgressIndicator
    private var textView: TextView

    init {
        inflate(context, R.layout.custom_progress_btn, this)
        progressBar = findViewById(R.id.progress_var)
        textView = findViewById(R.id.tv_text_progress)
        setState(State.DEFAULT)
    }

    fun setState(state: State) {
        when (state) {
            State.DEFAULT -> {
                progressBar.gone()
                textView.text = context.getString(R.string.login)
            }
            State.LOADING -> {
                progressBar.visible()
                textView.text = context.getString(R.string.loading)
            }
            State.DONE -> {
                progressBar.gone()
                textView.text = context.getString(R.string.successfully_login)
            }
        }
    }

    companion object {
        enum class State {
            DEFAULT, LOADING, DONE
        }
    }

}