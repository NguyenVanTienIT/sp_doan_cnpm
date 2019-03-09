package universityoftechnology.polytechnic.com.service_provider.Activity

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import universityoftechnology.polytechnic.com.service_provider.R
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.text.Layout
import android.text.SpannableString
import android.text.style.AlignmentSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.WindowManager
import universityoftechnology.polytechnic.com.service_provider.Fragment.TrangChu_Fragment


class HomeActivity : AppCompatActivity() {

    var optionMenu : NavigationView? = null
    var mDrawable : DrawerLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(universityoftechnology.polytechnic.com.service_provider.R.layout.activity_home)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        initView()
        initEvent()
    }

   fun initView(){
       mDrawable = findViewById(R.id.drawer_menu)
       optionMenu = findViewById(R.id.navigation_menu)
       optionMenu!!.bringToFront()
       resetMenu()

       supportFragmentManager.beginTransaction().add(R.id.layout_main, TrangChu_Fragment()).commit()
       optionMenu!!.setCheckedItem(R.id.home)
       var spanString : SpannableString = SpannableString(optionMenu!!.checkedItem!!.title.toString())
       spanString.setSpan( ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.item_slected)), 0, spanString.length, 0)
       optionMenu!!.checkedItem!!.setTitle(spanString)

   }

    fun initEvent(){
        optionMenu!!.setNavigationItemSelectedListener(object : NavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(p0: MenuItem): Boolean {  // set event click item menu
                var id = p0.itemId
                if(id == R.id.home){
                    resetMenu()
                    var spanString : SpannableString = SpannableString(p0.title.toString())
                    spanString.setSpan( ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.item_slected)), 0, spanString.length, 0)
                    p0.setTitle(spanString)
                    mDrawable!!.closeDrawer(Gravity.LEFT)
                }
                else if (id == R.id.circle){
                    resetMenu()
                    var spanString : SpannableString = SpannableString(p0.title.toString())
                    spanString.setSpan( ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.item_slected)), 0, spanString.length, 0)
                    p0.setTitle(spanString)
                    Log.d("Choose_Navigation", "Đã chọn")
                    mDrawable!!.closeDrawer(Gravity.LEFT)
                }
                else if (id == R.id.notification){
                    resetMenu()
                    var spanString : SpannableString = SpannableString(p0.title.toString())
                    spanString.setSpan( ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.item_slected)), 0, spanString.length, 0)
                    p0.setTitle(spanString)
                    mDrawable!!.closeDrawer(Gravity.LEFT)
                }
                else if (id == R.id.my_request){
                    resetMenu()
                    var spanString : SpannableString = SpannableString(p0.title.toString())
                    spanString.setSpan( ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.item_slected)), 0, spanString.length, 0)
                    p0.setTitle(spanString)
                    mDrawable!!.closeDrawer(Gravity.LEFT)
                }

                else if(id == R.id.setting){
                    resetMenu()
                    var spanString : SpannableString = SpannableString(p0.title.toString())
                    spanString.setSpan( ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.item_slected)), 0, spanString.length, 0)
                    p0.setTitle(spanString)
                    mDrawable!!.closeDrawer(Gravity.LEFT)
                }

                else if(id == R.id.log_out){
                    resetMenu()
                    var spanString : SpannableString = SpannableString(p0.title.toString())
                    spanString.setSpan( ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.item_slected)), 0, spanString.length, 0)
                    p0.setTitle(spanString)
                    mDrawable!!.closeDrawer(Gravity.LEFT)
                }
                else{
                    resetMenu()
                    var spanString : SpannableString = SpannableString(p0.title.toString())
                    spanString.setSpan( ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.item_slected)), 0, spanString.length, 0)
                    p0.setTitle(spanString)
                    mDrawable!!.closeDrawer(Gravity.LEFT)
                }
                return true
            }
        })
    }

    fun resetMenu(){
        for (i in 0..optionMenu!!.menu.size()-1) {
            var p0 = optionMenu!!.menu.getItem(i)
            var spanString : SpannableString = SpannableString(p0.getTitle().toString());
            spanString.setSpan( ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.none_selected)), 0, spanString.length, 0)
            p0.setTitle(spanString)
        }
    }
}
