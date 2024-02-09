package com.nitaioanmadalin.cosmodeviceexplorer.ui.bluetooth

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nitaioanmadalin.cosmodeviceexplorer.presentation.bluetooth.BluetoothViewModel
import com.nitaioanmadalin.cosmodeviceexplorer.ui.base.BaseComposeWrapperFragment
import com.nitaioanmadalin.cosmodeviceexplorer.ui.bluetooth.screen.ScanBluetoothScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BluetoothFragment: BaseComposeWrapperFragment() {

    private val viewModel by viewModels<BluetoothViewModel>()
    @Composable
    override fun FragmentContent(modifier: Modifier) {
        ScanBluetoothScreen(
            viewModel = viewModel,
            navigator = findNavController()
        )
    }
}