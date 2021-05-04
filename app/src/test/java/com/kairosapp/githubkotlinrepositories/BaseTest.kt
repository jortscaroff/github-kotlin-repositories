package com.kairosapp.githubkotlinrepositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.junit.runners.model.Statement
import org.mockito.junit.MockitoJUnitRunner

@RunWith(value = MockitoJUnitRunner::class)
abstract class BaseTest {
    @ExperimentalCoroutinesApi
    protected val testDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutinesUIThreadOverrideRule: TestRule = CoroutinesUIThreadOverrideRule(testDispatcher)

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

// ----------------------------------->

    @ExperimentalCoroutinesApi
    fun runBlockingTest(testBody: suspend TestCoroutineScope.() -> Unit) {
        kotlinx.coroutines.test.runBlockingTest(testDispatcher, testBody)
    }
}

class CoroutinesUIThreadOverrideRule(private val dispatcher: CoroutineDispatcher) : TestRule {

    @ExperimentalCoroutinesApi
    override fun apply(base: Statement, description: Description?): Statement = object : Statement() {
        override fun evaluate() {
            Dispatchers.setMain(dispatcher)

            base.evaluate()

            Dispatchers.resetMain()
        }
    }

}
