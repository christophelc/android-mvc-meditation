package cabinetcep.com.meditation

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import cabinetcep.com.meditation.databinding.ActivityMainBinding
import timber.log.Timber

const val KEY_TIMER = "timer"

//class MainActivity : WelcomeFragment.OnFragmentInteractionListener, AppCompatActivity()  {
class MainActivity : AppCompatActivity()  {


    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityMainBinding

    private fun initLog() {
        // Debug
        if (BuildConfig.DEBUG) {
            Timber.plant(LineNoDebugTree())
        }
        Timber.plant(CrashlyticsTree())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLog()
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout

        configureToolbar()
        configureNavigationDrawer()

//        val fragmentManager = getSupportFragmentManager()
//        fragmentManager.beginTransaction()
//            .replace(R.id.fragment_howto)

    }

    private fun configureToolbar() {
        val navController = this.findNavController(R.id.myNavHostFragment)
        val toolbar: Toolbar = binding.toolbar
        toolbar.setupWithNavController(navController, drawerLayout)
        setSupportActionBar(toolbar)
    }

    private fun configureNavigationDrawer() {
        val navController = this.findNavController(R.id.myNavHostFragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        val toolbar: Toolbar = binding.toolbar
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(binding.navView, navController)
        NavigationUI.setupWithNavController(toolbar, navController, drawerLayout)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

//    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
//        R.id.action_settings -> {
//            // User chose the "Settings" item, show the app settings UI...
//            true
//        }
//
//        R.id.action_info -> {
//            // User chose the "Favorite" action, mark the current item
//            // as a favorite...
//            Log.d(TAG, "info pressed")
//            true
//        }
//
//        R.id.greetingsFragment -> {
//            Log.d(TAG, "greetings pressed")
//            true
//        }
//
//        else -> {
//            Log.d(TAG, "menu")
//            // If we got here, the user's action was not recognized.
//            // Invoke the superclass to handle it.
//            super.onOptionsItemSelected(item)
//        }
//    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        Timber.d(item?.toString())
        if (item?.itemId == R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
            return true
        }
        return NavigationUI.onNavDestinationSelected(item!!,
            this.findNavController(R.id.myNavHostFragment))
                || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return navController.navigateUp()
    }
//    override fun onFragmentInteraction(uri: Uri) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
}
