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

import java.security.MessageDigest;

public class Hash
{
    enum CharactorSet {
        ALL,
        ALPHA_NUM,
    };

    public String makeHash(String src, CharactorSet cs, int length)
        throws java.io.UnsupportedEncodingException,
               java.security.NoSuchAlgorithmException {
        byte[] tmp = MessageDigest.getInstance("SHA-1")
                   .digest(src.getBytes("ASCII"));
        String hash = new Base64().encode(tmp);
        if (cs == CharactorSet.ALPHA_NUM) {
            hash = hash.replaceAll("[+/=]", "");
        } else {
            hash = hash.replaceAll("=", "");
        }
        if (length < hash.length()) {
            hash = hash.substring(0, length);
        }
        return hash;
    }
}
