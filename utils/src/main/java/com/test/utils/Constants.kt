package com.test.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri


const val WATSAPP_URL = "https://api.whatsapp.com/send?phone="
const val SPLASH_DELAY = 2000L
const val TAG_ID = "tag_id"

const val NAME_SPACE = "http://schemas.android.com/apk/res/android"
const val ORDER_PRICe = "order_price"
const val PAYMENT_STATUS = "payment"
const val FIREBASE_TOKEN= "firebaseToken"
const val TOTAL_ORDER_MONEY="total_money_order"

const val PRODUCT_DATA="product_data"
const val EDIT_PRODUCT="edit_product"

const val ORDER_ID = "order_id"
const val FROM_FAQ = "from_faq"
const val FROM_EXPRESS = "from_faq"
const val FROM_RATINGS = "from_ratings"
const val QUESTION_PRODUCTS = "question_products"

const val FAVOURITES = 1
const val UNFAVOURITES = 2
const val ACTIVE=1
const val INACTIVE=0
const val PINNED=1
const val UNPINNED=0
const val PRODUCT_STATUS= 1
const val PRODUCT_PIN = 2

const val VERIFY_NEW_USER = 1
const val VERIFY_CHANGE_PASSWORD = 2
const val STATUS_ORDER = "status_order"

/**
 * Model Types
 */

const val GENERAL = "general"
const val PROVIDER = "provider"
const val PRODUCT = "product"
const val EXTERNAL = "external"
const val ORDER = "order"


/**
 * order Status
 */
const val PENDING = 1
const val ACCEPTED = 2
const val WAITING = 3
const val PREPARING = 4
const val DELIVERING = 5
const val SHIPPING= 6
const val DELIVERED = 7
const val AUTO_CANCELLED = 8
const val CUSTOMER_CANCELLED = 9
const val SHOP_CANCELLD = 10

/**
 * Payment Status
 */

const val CASH_ON_DELIVERY = 1
const val ONLINE = 2
const val TRANSFER = 3
const val  WALLET = 4
/**
 * shared Prefrences
 */
const val TOKEN_USER = "token"
const val USER_ISVERIFIED = "user_verified"
const val CART_COUNTER = "cart_counter"
const val USER_DATA="userData"
const val WALLET_BALANCE="wallet_balance"

const val IS_PASSED_BY_ONBOARDING = "passed"
const val LANGUAGE_PREFRENCE = "lanugage"





/**
 * Bundles Tab
 */

const val CATEGORY_ID = "category_id"
const val CATEGORY_NAME = "category_name"
const val PRODUCT_ID = "product_id"
const val VENDOR_ID = "vendor_id"
const val VENDOR_NAME = "vendor_name"

const val CATEGORIES = "categories"
const val TAGS = "tags"
const val BANK = "bank"
const val SOCIAL_MEDIA_SETTINGS = "social_media"

const val MALE = "male"
const val FEMALE ="female"
const val FIRST_STEP_REGISTRATION = "first_step_registratin"

/**
 * products Tab
 */

const val TOTAL_IMAGES_ALLOWED = 4
const val TOTAL_TAGS_ALLOWED = 6

/**
 * settings Tab
 */

const val ARABIC = "ar"
const val ENGLISH = "en"






/**
 * login Data
 */
const val VERIFY_MOBILE = "verify_mobile"


const val LOGIN_CLASS_NAME = "com.homemade.login.LoginActivity"
const val SPLASH_CLASS_NAME = "com.homemade.user.SplashActivity"
const val FAVOURITES_CLASS_NAME = "com.homemade.main.menu.favourites.FavouriteActivity"
const val ORDERS_DETAILS_NAME = "com.homemade.orders.order_details.OrderDetailsActivity"
const val TERMS_AND_CONDITIONS = "com.homemade.main.menu.faq.FaqAndTermsActivity"
const val ADD_PRODUCT_FIRST = "com.homemade.product_data.AddProductFirstStepActivity"

object NavigationUtils{
    fun goToDestination(ctx1:Context,ctx: Class<*>){
        val intent = Intent(ctx1 ,ctx)
        ctx1.startActivity(intent)
        (ctx1 as Activity).finish()
    }

    fun goToDestination2(ctx1:Context,ctx: Class<*>){
        val intent = Intent(ctx1 ,ctx)
        ctx1.startActivity(intent)
    }
    fun goToDestinationWithClearTasks(ctx1:Context,ctx: Class<*>){
        val intent = Intent(ctx1 ,ctx)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        ctx1.startActivity(intent)
    }
    fun getProductDetailsUri(idToPass:Int): Uri {

        return Uri.parse("app://productDetails/${idToPass}")
    }
    fun getCategoryProducts(idToPass:Int,categoryName:String):Uri{
        return Uri.parse("app://categoryProducts/${idToPass}/${categoryName}")
    }
}

