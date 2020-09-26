package io.overfullstack.domain

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldNotBe


internal class DummyTest: StringSpec({
    "Dummy" {
        Dummy() shouldNotBe null
    }
})
