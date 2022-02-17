package com.infosupport.demos.h9.generics

import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class Generics7KtTest : WithAssertions {

    @Test
    fun assertThrowsDemoTest() {
        // jupiter's `assertThrows` is inline and has reified T
        val exception = assertThrows<IllegalArgumentException> { assertThrowsDemo() }

        assertThat(exception.message).isEqualTo("Reified T")
    }
}
