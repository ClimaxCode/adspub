package  com.climax.code.onBoarding

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.climax.code.utils.ConstantsCustomizations.onBoardingFullNativeAtIndex
import com.climax.code.utils.ConstantsCustomizations.onBoardingItemsList


class OnboardingFragmentStateAdapter(
    activity: AppCompatActivity,
    private val showFullNative: Boolean
) : FragmentStateAdapter(activity) {


    override fun getItemCount(): Int {
        return if (showFullNative) onBoardingItemsList.size + 1 else onBoardingItemsList.size
    }

    override fun createFragment(position: Int): Fragment {
        return when {
            showFullNative -> when {
                position == onBoardingFullNativeAtIndex -> FullNativeAdFragment() // Show FullNativeAdFragment at the specified index
                else -> getAdjustedFragment(position)
            }

            else -> when (position) {
                0 -> FirstFragment()
                1 -> SecondFragment()
                2 -> ThirdFragment()
                3 -> FourthFragment()
                4 -> FifthFragment()
                else -> {
                    FirstFragment()
                }
            }
        }
    }
    private fun getAdjustedFragment(position: Int): Fragment {
        val adjustedPosition = if (position > onBoardingFullNativeAtIndex) position - 1 else position
        return when (adjustedPosition) {
            0 -> FirstFragment()
            1 -> SecondFragment()
            2 -> ThirdFragment()
            3 -> FourthFragment()
            4 -> FifthFragment()
            else -> SixthFragment() // Add more fragments if needed
        }
    }
}
