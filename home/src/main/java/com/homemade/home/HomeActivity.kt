package com.homemade.home

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.constraintlayout.widget.Group
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.floriaapp.core.domain.model.User
import com.floriaapp.core.domain.model.general.settings
import com.floriaapp.core.ui.SplashViewModel
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView

import com.test.utils.*
import com.test.utils.Bases.BaseActivity
import com.test.utils.Bases.Communication
import com.test.utils.Ext.getObject
import com.test.utils.Ext.loadImage
import com.test.utils.Extensions.setMargins
import com.test.utils.adapter.Languages
import com.test.utils.adapter.LanguagesAdapter
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class HomeActivity : BaseActivity(), Communication, LanguagesAdapter.OnItemClickOfProduct {

    lateinit var bottomNavigationView: BottomNavigationView
    private val sharePrefrence: SharedPreferences.Editor by inject()
    private val sharePrefrenceGet: SharedPreferences by inject()
    var user: User? = null
    private val splashViewModel: SplashViewModel by viewModel()
    val adapter = LanguagesAdapter(this)
    lateinit var itemView: BottomNavigationItemView
    lateinit var cartBadge: View


    var resultLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
         //   bottomNavigationView.selectedItemId = (R.id.ordersListFragment)
        }
    }

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        // bottomNavigationView.disableShiftMode();
        val navController = findNavController(R.id.nav_host_fragment)
        bottomNavigationView.setupWithNavController(navController)

        bottomNavigationView.selectedItemId = R.id.homeFragment


//        findViewById<DrawerLayout>(R.id.drawer_layout_main).setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

//
//        findViewById<TextView>(R.id.tv_orders).setOnClickListener {
//            if (user != null) NavigationUtils.goToDestination2(this, OrdersActivity::class.java)
//        }


//        findViewById<TextView>(R.id.tv_questions).setOnClickListener {
//            if (user != null) NavigationUtils.goToDestination2(
//                this,
//                SubMenuQuestionsActivity::class.java
//            )
//        }

//        findViewById<TextView>(R.id.tv_about).setOnClickListener {
//            NavigationUtils.goToDestination2(this, SubMenuActivity::class.java)
//        }
//
//
//        findViewById<TextView>(R.id.tv_profile_locations).setOnClickListener {
//            NavigationUtils.goToDestination2(this, AddressesMenuActivity::class.java)
//        }
//
//        findViewById<TextView>(R.id.tv_profile_data).setOnClickListener {
//            if (user != null) NavigationUtils.goToDestination2(this, ProfileActivity::class.java)
//        }
//
//        findViewById<TextView>(R.id.tv_favourites).setOnClickListener {
//            if (user != null) NavigationUtils.goToDestination2(this, FavouriteActivity::class.java)
//        }

//        val settings = getObject(SOCIAL_MEDIA_SETTINGS, settings::class.java)
//
//        findViewById<ImageView>(R.id.iv_instgram).setOnClickListener {
//            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(settings?.instagram_url))
//            if (intent.resolveActivity(packageManager) != null) startActivity(browserIntent)
//        }
//        findViewById<ImageView>(R.id.iv_twitter).setOnClickListener {
//            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(settings?.twitter_url))
//            if (intent.resolveActivity(packageManager) != null) startActivity(browserIntent)
//        }
//
//
//        when (sharePrefrenceGet.getString(LANGUAGE_PREFRENCE, ARABIC)) {
//            ARABIC -> {
//                findViewById<TextView>(R.id.tv_language).text =
//                    resources.getString(R.string.arabic_title)
//                findViewById<TextView>(R.id.tv_language).setCompoundDrawablesRelativeWithIntrinsicBounds(
//                    R.drawable.ic_saudi_arabia,
//                    0,
//                    0,
//                    0
//                )
//
//            }
//            ENGLISH -> {
//                findViewById<TextView>(R.id.tv_language).text =
//                    resources.getString(R.string.english_title)
//                findViewById<TextView>(R.id.tv_language).setCompoundDrawablesRelativeWithIntrinsicBounds(
//                    R.drawable.ic_united_kingdom,
//                    0,
//                    0,
//                    0
//                )
//
//            }
//        }
//        findViewById<TextView>(R.id.tv_language).setOnClickListener {
//            showLanguages(adapter)
//        }


//        findViewById<ImageView>(R.id.iv_facebook).setOnClickListener {
//            try {
//                val browserIntent = Intent(
//                    Intent.ACTION_VIEW,
//                    Uri.parse(settings?.facebook_url ?: "https://facebook.com")
//                )
//                if (intent.resolveActivity(packageManager) != null) startActivity(browserIntent)
//            } catch (e: Exception) {
//
//            }
//
//        }


//        findViewById<TextView>(R.id.tv_sign_out).setOnClickListener {
//            clearTokens()
//            NavigationUtils.goToDestinationWithClearTasks(this, Class.forName(LOGIN_CLASS_NAME))
//
//        }


//        val tokenFirebase = sharePrefrenceGet.getString(FIREBASE_TOKEN, null)
//        if (tokenFirebase != null &&
//            sharePrefrenceGet.getString(TOKEN_USER, null) != null
//        )
//            splashViewModel.setFirebaseToken(tokenFirebase)


//        val mbottomNavigationMenuView: BottomNavigationMenuView =
//            bottomNavigationView.getChildAt(0) as BottomNavigationMenuView
//        val view: View = mbottomNavigationMenuView.getChildAt(3)
//
//        itemView = view as BottomNavigationItemView
//        cartBadge =
//            LayoutInflater.from(this).inflate(R.layout.badgelayout, bottomNavigationView, false)
//
//        itemView.addView(cartBadge)


    }

    private fun clearTokens() {
        sharePrefrence.remove(TOKEN_USER).remove(USER_ISVERIFIED).remove(CART_COUNTER)
            .remove(USER_DATA).remove(WALLET_BALANCE).apply()
    }


    @SuppressLint("SetTextI18n")
    private fun bindDataTouser(user: User?) {
        findViewById<TextView>(R.id.tv_email).text = user?.email ?: ""
        findViewById<TextView>(R.id.tv_user_name).text = user?.first_name + " " + user?.last_name
        findViewById<ImageView>(R.id.iv_profile).loadImage(user?.avatar, noPlaceHolder = true)

//        findViewById<TextView>(R.id.tv_wallet_data).setOnClickListener {
//            NavigationUtils.goToDestination2(this, WalletActivity::class.java)
//        }
        if (user == null) {
            findViewById<TextView>(R.id.tv_sign_out).text =
                resources.getString(R.string.sign_in)
            findViewById<Group>(R.id.group_home).visibility = View.GONE
            findViewById<TextView>(R.id.tv_about).setMargins(0, 20, 0, 0)
        }


    }


    fun setBadgeToCart() {
        val counter = sharePrefrenceGet.getInt(CART_COUNTER, 0)

        if (counter != 0) {
            // bottomNavigationView.getOrCreateBadge(R.id.cartFragment2).number = number.toInt()
            cartBadge.visibility = View.VISIBLE
            (cartBadge.findViewById<View>(R.id.notificationsBadge) as TextView).text =
                counter.toString()
            //itemView.addView(cartBadge)
            if (itemView.parent != null) {
                itemView.removeView(cartBadge)
                itemView.addView(cartBadge)
            }
            // val icon = bottomNavigationView.getOrCreateBadge(R.id.cartFragment2)
            // icon.backgroundColor = resources.getColor(R.color.red_color)
        } else {
            removeCartCounter(itemView)
            itemView.removeView(cartBadge)
            // bottomNavigationView.removeBadge(R.id.cartFragment2)


        }

    }

    private fun removeCartCounter(view: BottomNavigationItemView) {
        cartBadge.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
       // setBadgeToCart()

//        user = getObject(USER_DATA, User::class.java)
//        bindDataTouser(user)
    }


    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }

    override fun goToHomeTab() {
        bottomNavigationView.selectedItemId = (R.id.ordersListFragment)

    }

    override fun submitCartCount(cartCount: Int) {
        sharePrefrence.putInt(CART_COUNTER, cartCount).commit()
      //  bottomNavigationView.getOrCreateBadge(R.id.cart_navigation).number = cartCount
    }

    override fun goToProductTab() {
        bottomNavigationView.selectedItemId = (R.id.productsFragment2)

    }

    override fun goToCartTab() {
        invokeCartCounter()
//        bottomNavigationView.selectedItemId = (R.id.cartFragment2)

    }

    override fun goToServicesTab() {
        //  bottomNavigationView.selectedItemId = (R.id.services_navigation)
    }

    override fun invokeCartCounter() {
        setBadgeToCart()
    }

    override fun clearCartCounter() {
        removeCartCounter(itemView)

        //bottomNavigationView.removeBadge(R.id.cartFragment2)
    }

    override fun openDrawer() {
        findViewById<DrawerLayout>(R.id.drawer_layout_main).openDrawer(GravityCompat.START)

    }

    override fun goToProductsCategories(idProduct: Int, nameProduct: String) {

    }

    override fun goToProductDetails(idProduct: Int) {
    }

    override fun goToVendorsActivity(idVendor: Int) {
    }

//    override fun goToProductsCategories(idProduct: Int, nameProduct: String) {
//        val intent = Intent(this, ProductsCategoryActivity::class.java)
//        intent.putExtra(CATEGORY_ID, idProduct)
//        intent.putExtra(CATEGORY_NAME, nameProduct)
//        resultLauncher.launch(intent)
//    }
//
//    override fun goToProductDetails(idProduct: Int) {
//        val intent = Intent(this, ProductDetailsActivity::class.java)
//        intent.putExtra(PRODUCT_ID, idProduct)
//        resultLauncher.launch(intent)
//    }
//
//    override fun goToVendorsActivity(idVendor: Int) {
//        val intent = Intent(this, VendorActivity::class.java)
//        intent.putExtra(VENDOR_ID, idVendor)
//        resultLauncher.launch(intent)
//    }

    @SuppressLint("SetTextI18n", "CutPasteId")
    override fun setNewWalletBalance() {
        findViewById<TextView>(R.id.tv_wallet_data).text = resources.getString(R.string.wallet_data) + " "
        findViewById<TextView>(R.id.tv_wallet_data).append(
            Html.fromHtml(
                "<u> <font color='#682300'>${
                    sharePrefrenceGet.getInt(
                        WALLET_BALANCE,
                        0
                    ).toString() + " " + resources.getString(R.string.egp)} </font> </u>"
            )
        )
        //  findViewById<TextView>(R.id.tv_wallet_data).paintFlags = findViewById<TextView>(R.id.tv_wallet_data).paintFlags or Paint.UNDERLINE_TEXT_FLAG

    }

    override fun onPause() {
        super.onPause()
        if (findViewById<DrawerLayout>(R.id.drawer_layout_main).isDrawerOpen(GravityCompat.START)) {
            findViewById<DrawerLayout>(R.id.drawer_layout_main).close()
        }
    }

    override fun onAddNoteClicked(position: Int, item: Languages) {
        findViewById<TextView>(R.id.tv_language).text = item.title
        findViewById<TextView>(R.id.tv_language).setCompoundDrawablesRelativeWithIntrinsicBounds(
            item.flag,
            0,
            0,
            0
        )

        val isLanguageChanged = when (item.id) {
            1 -> {
                sharePrefrence.putString(LANGUAGE_PREFRENCE, ENGLISH).commit()
            }
            2 -> {
                sharePrefrence.putString(LANGUAGE_PREFRENCE, ARABIC).commit()
            }
            else -> sharePrefrence.putString(LANGUAGE_PREFRENCE, ARABIC).commit()
        }
        if (isLanguageChanged) {
            NavigationUtils.goToDestinationWithClearTasks(this, Class.forName(SPLASH_CLASS_NAME))
        }


    }
}