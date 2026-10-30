package com.example.expensetracker

import org.junit.Assert.assertTrue
import org.junit.Test

class PortfolioRepositoryTest {
    @Test
    fun starterRepositoryReturnsItems() {
        val items = PortfolioRepository().loadItems()
        assertTrue(items.isNotEmpty())
    }
}
