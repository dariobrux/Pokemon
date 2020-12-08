package com.dariobrux.pokemon.app.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.BounceInterpolator
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.dariobrux.pokemon.app.R
import com.dariobrux.pokemon.app.databinding.FragmentSplashBinding
import kotlinx.coroutines.*

/**
 *
 * Created by Dario Bruzzese on 24/11/2020.
 *
 * This fragment displays the splash screen.
 *
 */
class SplashFragment : Fragment() {

    private var binding: FragmentSplashBinding? = null

    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.let { binding ->
            binding.imgSplash.scaleX = 0f
            binding.imgSplash.scaleY = 0f
            binding.imgSplash.alpha = 0.3f
            binding.imgSplash.animate().scaleX(0.5f).scaleY(0.5f).alpha(0.75f).setDuration(800).setInterpolator(AnticipateOvershootInterpolator()).withEndAction {
                binding.imgSplash.animate().rotation(360f).scaleX(1f).scaleY(1f).alpha(1f).setDuration(1500).setInterpolator(BounceInterpolator()).start()
            }.start()
        }
    }

    override fun onResume() {
        super.onResume()
        activityScope.launch {
            delay(3000)
            NavHostFragment.findNavController(this@SplashFragment).navigate(R.id.action_splashFragment_to_mainFragment)
        }
    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}