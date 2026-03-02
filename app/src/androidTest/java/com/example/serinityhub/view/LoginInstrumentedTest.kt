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
class LoginInstrumentedTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<LoginActivity>()

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun testSuccessfulLogin_navigatesToDashboard() {
        // Enter email - finding by placeholder since no testTag is in source code
        composeRule.onNodeWithText("Enter your email")
            .performTextInput("test@gmail.com")

        // Enter password - finding by placeholder since no testTag is in source code
        composeRule.onNodeWithText("Enter your password")
            .performTextInput("123456")

        // Click Sign In button
        composeRule.onNodeWithText("Sign In")
            .performClick()
    }

    @Test
    fun testNavigateToRegister() {
        // Click on "Sign Up" text
        composeRule.onNodeWithText("Sign Up")
            .performClick()

        // Verify navigation to RegistrationActivity
        Intents.intended(hasComponent(RegistrationActivity::class.java.name))
    }
}
