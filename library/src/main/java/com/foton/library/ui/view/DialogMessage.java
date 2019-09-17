package com.foton.library.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.foton.library.R;

public class DialogMessage extends Dialog {
    private TextView mContentTv;
    private Button mConfirmBtn;

    public DialogMessage(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_message);
        mContentTv = findViewById(R.id.contentTv);
        mConfirmBtn = findViewById(R.id.confirmBtn);

        mConfirmBtn.setOnClickListener(v -> this.dismiss());
    }

    public void setContent(String content) {
        if (mContentTv != null) {
            mContentTv.setText(content);
        }
    }
}
