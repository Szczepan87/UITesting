package com.example.datepickertrial

import org.junit.Assert.assertTrue
import org.junit.Test

class FillFieldTest {

    @Test
    fun is_text_field_ok(){
        assertTrue(Verificator().isFieldOk(login_inputText))
    }
}