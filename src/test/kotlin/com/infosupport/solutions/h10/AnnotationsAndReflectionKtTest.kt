package com.infosupport.solutions.h10

import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

internal class AnnotationsAndReflectionKtTest : WithAssertions {

    @Test
    fun testGet() {
        val personService = get<PersonService>()
        assertThat(personService).isNotNull
        // assertThat(personService?.dao).isNotNull
    }
}
