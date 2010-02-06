//
// Copyright (c) 2010 Satoshi Fukutomi <info@fuktommy.com>.
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

package com.fuktommy.genpasswd;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PasswordGenerator extends Activity
{
    private final static int MENU_ITEM_ABOUT = 0;
    private final static int CHARACTOR_ALL = 0;
    private final static int CHARACTOR_ALPHA_NUM = 1;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
 
        setGenerateButtonClickListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuItem aboutItem = menu.add(0, MENU_ITEM_ABOUT, 0, R.string.about);
        aboutItem.setIcon(android.R.drawable.ic_menu_info_details);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ITEM_ABOUT:
                displayAbout();
                return true;
        }
        return true;
    }

    private void setGenerateButtonClickListener() {
        final Button button = (Button) findViewById(R.id.generate_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                generatePassword();
            }
        });
    }

    private String getTextViewValue(int id) {
        return ((TextView) findViewById(id)).getText().toString();
    }

    private String getSpinnerValue(int id) {
        Spinner spinner = (Spinner) findViewById(id);
        TextView textView = (TextView) spinner.getSelectedView();
        return textView.getText().toString();
    }

    private int getSpinnerValueInt(int id) {
        return Integer.parseInt(getSpinnerValue(id));
    }

    private int getSpinnerValueCharactorSet(int id) {
        String str = getSpinnerValue(id);
        if (str.indexOf('+') >= 0) {
            return CHARACTOR_ALL;
        } else {
            return CHARACTOR_ALPHA_NUM;
        }
    }

    private void generatePassword() {
        String domain = getTextViewValue(R.id.domain_field);
        String passphrase = getTextViewValue(R.id.passphrase_field);
        String salt = getTextViewValue(R.id.salt_field);
        int charactor = getSpinnerValueCharactorSet(R.id.charactor_field);
        int length = getSpinnerValueInt(R.id.length_field);

        String password = domain + ":" + passphrase + ":"
                        + salt + ":" + charactor + ":" + length;

        ClipboardManager cm 
            = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE); 
        cm.setText(password);

        String format = getText(R.string.copy_to_clipboard).toString();
        String message = String.format(format, password);

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void displayAbout() {
        DialogInterface.OnClickListener ocl
            = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int whichButton) {
                    setResult(RESULT_OK);
                }
            };

        String message = getText(R.string.about_message).toString()
                       + "\n\n\n"
                       + getText(R.string.license).toString();
        new AlertDialog.Builder(this)
            .setTitle(R.string.about)
            .setMessage(message)
            .setNeutralButton(R.string.ok, ocl)
            .create().show();
    }
}
