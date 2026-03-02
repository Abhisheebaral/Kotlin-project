package com.example.serinityhub.view

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterInstrumentedTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<RegistrationActivity>()

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun testSuccessfulRegistrationUI_interactsCorrectly() {
        // Enter email - finding by placeholder
        composeRule.onNodeWithText("Enter your email")
            .performTextInput("test_user@gmail.com")

        // Enter password - finding by placeholder
        composeRule.onNodeWithText("Create password")
            .performTextInput("password123")

        // Click on Register button
        composeRule.onNodeWithText("Register")
            .performClick()
    }

    @Test
    fun testNavigateToLogin() {
        // Click on "Sign In" text
        composeRule.onNodeWithText("Sign In")
            .performClick()

        // Verify navigation back to LoginActivity
        Intents.intended(hasComponent(LoginActivity::class.java.name))
    }
}
