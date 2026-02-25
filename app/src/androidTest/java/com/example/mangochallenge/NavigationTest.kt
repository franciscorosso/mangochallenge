package com.example.mangochallenge

import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NavigationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun bottomNavigation_productsTabIsSelectedByDefault() {
        composeRule.onNodeWithText("Productos").assertIsSelected()
    }

    @Test
    fun bottomNavigation_navigateToFavorites() {
        composeRule.onNodeWithText("Favoritos").performClick()
        composeRule.onNodeWithText("Favoritos").assertIsSelected()
    }

    @Test
    fun bottomNavigation_navigateToProfile() {
        composeRule.onNodeWithText("Perfil").performClick()
        composeRule.onNodeWithText("Perfil").assertIsSelected()
    }
}
