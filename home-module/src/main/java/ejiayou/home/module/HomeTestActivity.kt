package ejiayou.home.module

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * @author:
 * @created on: 2022/7/22 17:50
 * @description:
 */
class HomeTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_test_activity)
        findViewById<TextView>(R.id.home_test_start).setOnClickListener {
            startActivity(Intent(HomeTestActivity@ this, HomeMainActivity::class.java))
        }
    }
}