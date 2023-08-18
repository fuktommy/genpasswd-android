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

package com.fuktommy.genpasswd2;

import junit.framework.TestCase;

public class HashTest extends TestCase {

    public void testMakeHash() throws Exception {
        String result = new Hash().makeHash(
                "foo:bar", Hash.CharacterSet.ALL, 27);
        assertEquals("VNy+Z9IdXrOUk9Rtia4fQS071t4", result);
    }

    public void testMakeHashAlpha() throws Exception {
        String result = new Hash().makeHash(
                "foo:bar", Hash.CharacterSet.ALPHA_NUM, 27);
        assertEquals("VNyZ9IdXrOUk9Rtia4fQS071t4", result);
    }

    public void testMakeHashSize() throws Exception {
        String result = new Hash().makeHash(
                "foo:bar", Hash.CharacterSet.ALL, 6);
        assertEquals("VNy+Z9", result);
    }

    public void testMakeHashAlphaSize() throws Exception {
        String result = new Hash().makeHash(
                "foo:bar", Hash.CharacterSet.ALPHA_NUM, 6);
        assertEquals("VNyZ9I", result);
    }

}
