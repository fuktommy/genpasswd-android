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

import junit.framework.TestCase;

public class Base64Test extends TestCase {

    public void testEncode1() {
        assertEquals("YQ==", new Base64().encode("a".getBytes()));
    }

    public void testEncode2() {
        assertEquals("YmM=", new Base64().encode("bc".getBytes()));
    }

    public void testEncode3() {
        assertEquals("ZGVm", new Base64().encode("def".getBytes()));
    }

    public void testEncode4() {
        assertEquals("Z2hpag==", new Base64().encode("ghij".getBytes()));
    }

    public void testEncode10() {
        assertEquals("MTIzNDU2Nzg5MA==",
                new Base64().encode("1234567890".getBytes()));
    }

    public void testEncode20() {
        final String src = "ABCDEFGHIJKLMNOPQRST";
        assertEquals("QUJDREVGR0hJSktMTU5PUFFSU1Q=",
                new Base64().encode(src.getBytes()));
    }

    public void testEncodeBinary() {
        final byte[] src = new byte[] {
                (byte)84, (byte)220, (byte)190, (byte)103,
        };
        assertEquals("VNy+Zw==", new Base64().encode(src));
    }
}
