package com.spravochnic.scbguide.ui.test

import com.spravochnic.scbguide.api.models.TestCategories


sealed class TestState {
    object Empty: TestState()
    data class setDataTest(val data: List<TestCategories>): TestState()
}

enum class TransactionTestEvent {
    NAVIGATION
}