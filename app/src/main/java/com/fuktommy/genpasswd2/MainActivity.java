//
// Copyright (c) 2010 Satoshi Fukutomi.
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions
// are met:
// 1. Redistributions of source code must retain the above copyright
//    notice, this list of conditions and the following disclaimer.
// 2. Redistributions in binary form must reproduce the above copyright
//    notice, this list of conditions and the following disclaimer in the
//    documentation and/or other materials provided with the distribution.
//
// THIS SOFTWARE IS PROVIDED BY THE AUTHORS AND CONTRIBUTORS ``AS IS'' AND
// ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED.  IN NO EVENT SHALL THE AUTHORS OR CONTRIBUTORS BE LIABLE
// FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
// DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
// OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
// HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
// LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
// OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE.
//

package com.fuktommy.genpasswd2;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;

import java.net.URL;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity
{
    private final static int MENU_ITEM_ABOUT = 0;
    private final static int MENU_ITEM_LICENSE = 1;

    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar tb = findViewById(R.id.my_toolbar);
        setSupportActionBar(tb);

        setGenerateButtonClickListener();
        loadIntent();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_ITEM_ABOUT, 0, R.string.about);
        menu.add(0, MENU_ITEM_LICENSE, 0, R.string.license);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == MENU_ITEM_ABOUT) {
            displayAbout();
            return true;
        }
        if (item.getItemId() == MENU_ITEM_LICENSE) {
           startActivity(new Intent(this, OssLicensesMenuActivity.class));
           return false;
        }
        return true;
    }

    private void setGenerateButtonClickListener() {
        final Button button = findViewById(R.id.generate_button);
        button.setOnClickListener(v -> {
            try {
                generatePassword();
            } catch (Exception e) {
                displayError(e.getMessage());
            }
        });
    }

    private void loadIntent() {
        final Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        final String uri = extras.getString(Intent.EXTRA_TEXT);
        if (uri == null) {
            return;
        }
        try {
            final String host = new URL(uri).getHost();
            ((TextView) findViewById(R.id.domain_field)).setText(host);
        } catch (java.net.MalformedURLException ignored) {
        }
    }

    private String getTextViewValue(final int id) {
        return ((TextView) findViewById(id)).getText().toString();
    }

    private String getSpinnerValue(final int id) {
        final Spinner spinner = findViewById(id);
        final TextView textView = (TextView) spinner.getSelectedView();
        return textView.getText().toString();
    }

    private int getSpinnerValueInt(final int id) {
        return Integer.parseInt(getSpinnerValue(id));
    }

    private Hash.CharacterSet getSpinnerValueCharacterSet(final int id) {
        final String str = getSpinnerValue(id);
        if (str.indexOf('+') >= 0) {
            return Hash.CharacterSet.ALL;
        } else {
            return Hash.CharacterSet.ALPHA_NUM;
        }
    }

    private void generatePassword()
            throws NoSuchAlgorithmException {
        final String domain = getTextViewValue(R.id.domain_field);
        final String passphrase = getTextViewValue(R.id.passphrase_field);
        final String salt = getTextViewValue(R.id.salt_field);
        Hash.CharacterSet character
                = getSpinnerValueCharacterSet(R.id.charactor_field);
        final int length = getSpinnerValueInt(R.id.length_field);

        String src;
        if (!salt.isEmpty()) {
            src = domain + ':' + salt + ':' + passphrase;
        } else {
            src = domain + ':' + passphrase;
        }
        final String password = new Hash().makeHash(src, character, length);

        final ClipData cd = ClipData.newPlainText("password", password);
        final PersistableBundle extras = new PersistableBundle();
        extras.putBoolean("android.content.extra.IS_SENSITIVE", true);
        cd.getDescription().setExtras(extras);
        final ClipboardManager cm
                = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        cm.setPrimaryClip(cd);

        final String format = getText(R.string.copy_to_clipboard).toString();
        final String message = String.format(format, password);

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void displayDialog(final int title, final String message) {
        final DialogInterface.OnClickListener ocl
                = (dialog, whichButton) -> setResult(RESULT_OK);
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setNeutralButton(R.string.ok, ocl)
                .create().show();
    }

    private void displayAbout() {
        final String message = (getText(R.string.about_message)
                + "\n\n\n"
                + getText(R.string.license_message))
            .replaceAll("\n +", "\n");
        displayDialog(R.string.about, message);
    }

    private void displayError(final String message) {
        displayDialog(R.string.error, message);
    }
}
