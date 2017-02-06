package com.example.antony.rssreader.screens;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.antony.rssreader.R;
import com.example.antony.rssreader.models.MenuItem;

/**
 * Created by Antony on 2/6/2017.
 */

public class AddMenuItemDialog extends AppCompatDialogFragment {
    private AddMenuListener mListener;
    private TextInputLayout mLinkInputLayout;
    private TextInputLayout mNameInputLayout;
    private EditText mLinkEditText;
    private EditText mNameEditText;
    private Button mAddButton;
    private Button mClearButton;

    public interface AddMenuListener {
        void onItemAdded(MenuItem item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof AddMenuListener)) {
            throw new IllegalStateException("Must implement AddMenuListener");
        }
        mListener = (AddMenuListener) context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle(R.string.add_feed);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(AppCompatDialogFragment.STYLE_NORMAL, R.style.DialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_feed_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mLinkInputLayout = (TextInputLayout) view.findViewById(R.id.textInputLayoutLink);
        mNameInputLayout = (TextInputLayout) view.findViewById(R.id.textInputLayoutName);
        mAddButton = (Button) view.findViewById(R.id.btnAdd);
        mClearButton = (Button) view.findViewById(R.id.clearBtn);
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNameEditText.getText().clear();
                mLinkEditText.getText().clear();
            }
        });
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              validateInput();
            }
        });
        mNameEditText = mNameInputLayout.getEditText();
        mLinkEditText = mLinkInputLayout.getEditText();
    }

    private void validateInput() {
        Editable name = mNameEditText.getText();
        Editable link = mLinkEditText.getText();
        if (name.length() < 3) {
            showMessage("Name for feed must be at least 3 characters");
            return;
        }
        if (link.length() == 0) {
            showMessage("Link must not be empty");
            return;
        }
        if (!link.toString().startsWith("http://feeds.gawker.com")) {
            showMessage("Only accepting feeds from feeds.gawker.com at this time");
            return;
        }
        MenuItem menuItem = new MenuItem(name.toString(), link.toString());
        mListener.onItemAdded(menuItem);
        dismiss();
    }

    private void showMessage(String s) {
        Snackbar.make(mAddButton, s, Snackbar.LENGTH_SHORT).show();
    }
}
