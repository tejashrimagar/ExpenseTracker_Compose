package com.example.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PortfolioItem(val id: Int, val title: String, val subtitle: String)

class PortfolioRepository @Inject constructor() {
    fun loadItems(): List<PortfolioItem> = listOf(
        PortfolioItem(1, "Monthly overview", "Add income and expenses in the next milestone"),
        PortfolioItem(2, "Offline-first direction", "Next: Room entities, DAO and repository"),
        PortfolioItem(3, "Insights", "Next: categories, filters and monthly summaries")
    )
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: PortfolioRepository
) : ViewModel() {
    private val _items = MutableStateFlow(repository.loadItems())
    val items: StateFlow<List<PortfolioItem>> = _items.asStateFlow()
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    HomeScreen()
                }
            }
        }
    }
}

@Composable
fun HomeScreen(viewModel: HomeViewModel = androidx.hilt.navigation.compose.hiltViewModel()) {
    val items by viewModel.items.collectAsState()
    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        Text("Expense Tracker", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(6.dp))
        Text("Personal portfolio project • Compose • MVVM")
        Spacer(Modifier.height(18.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(items, key = { it.id }) { item ->
                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text(item.title, style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(4.dp))
                        Text(item.subtitle, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}
