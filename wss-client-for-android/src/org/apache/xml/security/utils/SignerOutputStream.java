/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.xml.security.utils;

import java.io.ByteArrayOutputStream;

import org.apache.xml.security.algorithms.SignatureAlgorithm;
import org.apache.xml.security.signature.XMLSignatureException;

/**
 * @author raul
 *
 */
public class SignerOutputStream extends ByteArrayOutputStream {
    private static de.kp.logging.Log log =
        de.kp.logging.LogFactory.getLog(SignerOutputStream.class);
    
    final SignatureAlgorithm sa;

    /**
     * @param sa
     */
    public SignerOutputStream(SignatureAlgorithm sa) {
        this.sa = sa;       
    }

    /** @inheritDoc */
    public void write(byte[] arg0)  {
        try {
            sa.update(arg0);
        } catch (XMLSignatureException e) {
            throw new RuntimeException("" + e);
        }
    }

    /** @inheritDoc */
    public void write(int arg0) {
        try {
            sa.update((byte)arg0);
        } catch (XMLSignatureException e) {
            throw new RuntimeException("" + e);
        }
    }

    /** @inheritDoc */
    public void write(byte[] arg0, int arg1, int arg2) {
        if (log.isDebugEnabled()) {
            log.debug("Canonicalized SignedInfo:");
            StringBuilder sb = new StringBuilder(arg2);
            for (int i = arg1; i < (arg1 + arg2); i++) {
                sb.append((char)arg0[i]);
            }
            log.debug(sb.toString());
        }
        try {
            sa.update(arg0, arg1, arg2);
        } catch (XMLSignatureException e) {
            throw new RuntimeException("" + e);
        }
    }
}
