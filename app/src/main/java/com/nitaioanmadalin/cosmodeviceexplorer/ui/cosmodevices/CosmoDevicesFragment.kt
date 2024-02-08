package com.nitaioanmadalin.cosmodeviceexplorer.ui.cosmodevices

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.nitaioanmadalin.cosmodeviceexplorer.ui.base.BaseComposeWrapperFragment
import com.nitaioanmadalin.cosmodeviceexplorer.ui.cosmodevices.screen.DevicesScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CosmoDevicesFragment: BaseComposeWrapperFragment() {

    private val viewModel by activityViewModels<CosmoDevicesViewModel>()

    @Composable
    override fun FragmentContent(modifier: Modifier) {
        DevicesScreen(
            viewModel = viewModel,
            onDeviceClicked = {
                val action = CosmoDevicesFragmentDirections.devicesToDetails(it)
                findNavController().navigate(action)
            }
        )
    }
}