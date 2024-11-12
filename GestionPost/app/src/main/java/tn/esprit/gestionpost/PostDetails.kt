import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import tn.esprit.gestionpost.R


class PostDetails : AppCompatActivity() {

    private lateinit var friendsButton: ImageButton
    private lateinit var meButton: ImageButton
    private lateinit var allButton: ImageButton
    private lateinit var stateButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        // Find references to buttons
        friendsButton = findViewById(R.id.friends)
        meButton = findViewById(R.id.me)
        allButton = findViewById(R.id.all)
        stateButton = findViewById(R.id.state)

        // Set initial visibility
        friendsButton.visibility = View.GONE
        meButton.visibility = View.GONE
        allButton.visibility = View.GONE
        stateButton.visibility = View.VISIBLE
    }

    fun onStateButtonClick(view: View) {
        stateButton.visibility = View.GONE

        friendsButton.visibility = View.VISIBLE
        meButton.visibility = View.VISIBLE
        allButton.visibility = View.VISIBLE
    }

    fun onOtherButtonClick(view: View) {
        stateButton.visibility = View.VISIBLE

        friendsButton.visibility = View.GONE
        meButton.visibility = View.GONE
        allButton.visibility = View.GONE
    }
}
