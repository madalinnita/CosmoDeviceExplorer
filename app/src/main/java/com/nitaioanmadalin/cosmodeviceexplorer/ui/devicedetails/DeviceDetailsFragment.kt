package com.nitaioanmadalin.cosmodeviceexplorer.ui.devicedetails

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nitaioanmadalin.cosmodeviceexplorer.ui.base.BaseComposeWrapperFragment
import com.nitaioanmadalin.cosmodeviceexplorer.ui.devicedetails.screen.DeviceDetailsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeviceDetailsFragment: BaseComposeWrapperFragment() {

    private val args: DeviceDetailsFragmentArgs by navArgs()

    @Composable
    override fun FragmentContent(modifier: Modifier) {
        DeviceDetailsScreen(
            cosmoDevice = args.deviceInfo,
            navigator = findNavController()
        )
    }
}