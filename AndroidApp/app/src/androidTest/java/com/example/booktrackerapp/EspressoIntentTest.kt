package com.example.booktrackerapp

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EspressoIntentTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun cleanup() {
        Intents.release()
    }

    @Test
    fun testAddBookIntentNavigation() {
        composeTestRule.onNodeWithText("Add New Book")
            .performClick()

        Espresso.onView(ViewMatchers.isRoot())
            .check { view, _ ->
                assert(view != null)
            }
        Espresso.pressBack()

        composeTestRule.onNodeWithText("My Reading Library")
            .assertExists()
    }
}
