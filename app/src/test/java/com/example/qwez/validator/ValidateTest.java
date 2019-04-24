package com.example.qwez.validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ValidateTest {

    EmailValidate emailValidate = new EmailValidate();
    PasswordValidate passwordValidate = new PasswordValidate();
    EmptyValidate emptyValidate = new EmptyValidate();

    @Test
    public void emailTest() {
        assertTrue( emailValidate.isValid("myemail@gmail.com"));
        assertFalse( emailValidate.isValid("myemailgmailcom"));
    }

    @Test
    public void passwordTest() {
        assertTrue( passwordValidate.isValid("HejHejHej123#"));
        assertFalse( passwordValidate.isValid("hej"));
        assertFalse( passwordValidate.isValid("hejhejhejhejhej"));
        assertFalse( passwordValidate.isValid("123"));
        assertFalse( passwordValidate.isValid("13123123123123123"));
        assertFalse( passwordValidate.isValid("???"));
        assertFalse( passwordValidate.isValid("?????????????????"));
        assertFalse( passwordValidate.isValid("HHHHHHHHHHHHHHHHH"));
        assertFalse( passwordValidate.isValid("hhhhhhhhhhhhhhhhhh"));
    }

    @Test
    public void emptyTest() {
        assertTrue( emptyValidate.isValid("hej"));
        assertFalse( emptyValidate.isValid(""));
    }

    @Test
    public void errorMessageTest(){
        assertTrue(emailValidate.hasErrorMessage());
        assertTrue(passwordValidate.hasErrorMessage());
        assertTrue(emptyValidate.hasErrorMessage());
    }
}