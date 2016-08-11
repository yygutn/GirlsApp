package cn.edu.jumy.girls.ui.activity

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.transition.Transition
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import butterknife.BindView
import cn.edu.jumy.girls.R
import cn.edu.jumy.girls.common.base.BaseMvpActivity
import cn.edu.jumy.girls.presenter.GirlDetailPresenter
import cn.edu.jumy.girls.ui.view.GirlDetailView
import cn.edu.jumy.girls.util.AndroidUtils
import cn.edu.jumy.girls.util.ToastUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.toolbar.*
import uk.co.senab.photoview.PhotoViewAttacher

/**
 * User: Jumy (yygutn@gmail.com)
 * Date: 16/8/11  下午6:43
 */
class GirlDetailActivity : BaseMvpActivity<GirlDetailView, GirlDetailPresenter>(), GirlDetailView {
    companion object {
        private val EXTRA_BUNDLE_URL = "BUNDLE_URL"
        private val EXTRA_BUNDLE_TITLE = "BUNDLE_TITLE"

        private val VIEW_NAME_HEADER_IMAGE = "detail:header:image"
        private val VIEW_NAME_HEADER_TITLE = "detail:header:title"

        fun gotoGirlDetail(context: AppCompatActivity, url: String, title: String, imageView: View, viewText: View) {
            val intent = Intent(context, GirlDetailActivity::class.java)
            intent.putExtra(EXTRA_BUNDLE_URL, url)
            intent.putExtra(EXTRA_BUNDLE_TITLE, title)

            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    context, Pair<View, String>(imageView,
                    VIEW_NAME_HEADER_IMAGE),
                    Pair(viewText,
                            VIEW_NAME_HEADER_TITLE))

            ActivityCompat.startActivity(context, intent, activityOptions.toBundle())
        }
    }
    lateinit var mImageView: ImageView

    private var mUrl: String = ""
    var mAttacher: PhotoViewAttacher? = null

    override fun getLayout(): Int {
        return R.layout.activity_girl_detail
    }

    override fun viewBinding() {
        super.viewBinding()
        mImageView = findViewById(R.id.iv_girl_detail) as ImageView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAttacher = PhotoViewAttacher(mImageView)

        mUrl = intent.getStringExtra(EXTRA_BUNDLE_URL)
        setTitle(intent.getStringExtra(EXTRA_BUNDLE_TITLE),true)

        ViewCompat.setTransitionName(mImageView, VIEW_NAME_HEADER_IMAGE)
        ViewCompat.setTransitionName(AndroidUtils.getTitleViewInToolbar(mToolbar), VIEW_NAME_HEADER_TITLE)

        loadItem()
    }

    override fun getMenuRes(): Int {
        return R.menu.menu_girl_detail
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_save){
            presenter.saveFace(intent.getStringExtra(EXTRA_BUNDLE_URL))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadItem() {
        if (AndroidUtils.isAndroidL() && addTransitionListener()) {
            loadThumbnail()
        } else {
            loadFullSizeImage()
        }
    }

    private fun loadThumbnail() {
        Picasso.with(this).load(mUrl).noFade().into(mImageView)
    }

    private fun loadFullSizeImage() {
        Picasso.with(this).load(mUrl).noFade().noPlaceholder().into(mImageView)
    }

    override fun saveSuccess(message: String) {
        showToast(message)

    }

    override fun showFailInfo(error: String) {
        showToast(error)
    }

    override fun createPresenter(): GirlDetailPresenter {
        return GirlDetailPresenter(mContext)
    }

    override fun onDestroy() {
        super.onDestroy()
        Picasso.with(mContext).cancelRequest(mImageView)
    }

    /**
     * Try and add a [Transition.TransitionListener] to the entering shared element
     * [Transition]. We do this so that we can load the full-size image after the transition
     * has completed.
     * @return true if we were successful in adding a listener to the enter transition
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun addTransitionListener(): Boolean {
        val transition = window.sharedElementEnterTransition

        if (transition != null) {
            // There is an entering shared element transition so add a listener to it
            transition.addListener(object : Transition.TransitionListener {
                override fun onTransitionEnd(transition: Transition) {
                    // As the transition has ended, we can now load the full-size image
                    loadFullSizeImage()

                    // Make sure we remove ourselves as a listener
                    transition.removeListener(this)
                }

                override fun onTransitionStart(transition: Transition) {
                    // No-op
                }

                override fun onTransitionCancel(transition: Transition) {
                    // Make sure we remove ourselves as a listener
                    transition.removeListener(this)
                }

                override fun onTransitionPause(transition: Transition) {
                    // No-op
                }

                override fun onTransitionResume(transition: Transition) {
                    // No-op
                }
            })
            return true
        }
        // If we reach here then we have not added a listener
        return false
    }
}