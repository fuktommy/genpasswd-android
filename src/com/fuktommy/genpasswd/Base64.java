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

public class Base64 {
    static final char[] char64 = new char[] {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
		'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
		'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
		'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
		'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
		'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
		'w', 'x', 'y', 'z', '0', '1', '2', '3',
		'4', '5', '6', '7', '8', '9', '+', '/'
    };

    private byte[] encodeUnit(byte[] src) {
        int length = src.length;
        byte[] tmp = new byte[3];
        for (int i = 0; i < 3; i++) {
            tmp[i] = (i < length) ? src[i] : (byte)0;
        }
        int[] dst = new int[4];

        dst[0] = 0x3F & ((0xFF & tmp[0]) >>> 2);
        dst[1] = 0x3F & (((0x03 & tmp[0]) << 4) | ((0xFF & tmp[1]) >> 4));
        dst[2] = 0x3F & (((0x0F & tmp[1]) << 2) | ((0xFF & tmp[2]) >> 6));
        dst[3] = 0x3F & tmp[2];

        byte[] ret = new byte[4];
        for (int i = 0; i < length + 1; i++) {
            ret[i] = (byte)char64[dst[i]];
        }
        for (int i = length + 1; i < 4; i++) {
            ret[i] = (byte)'=';
        }
        return ret;
    }

    String encode(byte[] src) throws java.io.UnsupportedEncodingException {
        int length = src.length;
        StringBuffer ret = new StringBuffer();
        for (int i = 0; i < length; i += 3) {
            int size = (i + 3 < length) ? 3 : length - i;
            byte[] tmp = new byte[size];
            System.arraycopy(src, i, tmp, 0, size);
            ret.append(new String(encodeUnit(tmp), "ASCII"));
        }
        return ret.toString();
    }
}
