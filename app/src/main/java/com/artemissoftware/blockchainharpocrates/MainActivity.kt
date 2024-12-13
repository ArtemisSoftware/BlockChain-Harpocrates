package com.artemissoftware.blockchainharpocrates

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.artemissoftware.blockchainharpocrates.datastore.source.DataStoreSource
import com.artemissoftware.blockchainharpocrates.repository.BlockChainRepository
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
