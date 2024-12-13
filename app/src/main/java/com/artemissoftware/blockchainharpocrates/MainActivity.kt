package com.artemissoftware.blockchainharpocrates

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.artemissoftware.blockchainharpocrates.datastore.source.DataStoreSource
import com.artemissoftware.blockchainharpocrates.ui.theme.BlockchainHarpocratesTheme

class MainActivity : ComponentActivity() {

    private val repository by lazy {
        BlockChainRepository(dataSource = DataStoreSource(applicationContext))
    }
    private val viewModel by lazy {
        BlockchainViewModel(repository = repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BlockchainHarpocratesTheme {
                BlockchainScreen(viewModel = viewModel)
            }
        }
    }
}
