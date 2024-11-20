package  com.climax.code.onBoarding

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class OnboardingFragmentStateAdapter(activity: AppCompatActivity, private val showAllFragments: Boolean) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return if (showAllFragments) 4 else 3
    }
    override fun createFragment(position: Int): Fragment {
        return when {
            showAllFragments -> when (position) {
                0 -> FirstFragment()
                1 -> SecondFragment()
                2 -> FullNativeAdFragment()
                3 -> ThirdFragment()
                else ->{
                    FirstFragment()
                }
            }
            else -> when (position) {
                0 -> FirstFragment()
                1 -> SecondFragment()
                2 -> ThirdFragment()
                else ->{
                    FirstFragment()
                }
            }
        }
    }

}
