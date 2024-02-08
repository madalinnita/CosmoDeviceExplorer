package com.nitaioanmadalin.cosmodeviceexplorer.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.nitaioanmadalin.cosmodeviceexplorer.ui.theme.CosmoDeviceExplorerTheme
abstract class BaseComposeWrapperFragment: Fragment() {

    @Composable
    abstract fun FragmentContent(modifier: Modifier)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                CosmoDeviceExplorerTheme {
                    FragmentContent(modifier = Modifier)
                }
            }
        }
    }
}