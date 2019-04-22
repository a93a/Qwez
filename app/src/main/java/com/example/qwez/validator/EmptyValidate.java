package com.example.qwez.validator;

import android.text.TextUtils;
import android.widget.EditText;

public class EmptyValidate extends Validate {

    private static final String EMPTY_ERROR_MESSAGE = "Field cannot be empty";

    public EmptyValidate() {
        super(EMPTY_ERROR_MESSAGE);
    }

    @Override
    public boolean isValid(EditText editText) {
        return TextUtils.getTrimmedLength(editText.getText()) > 0;
    }

}
