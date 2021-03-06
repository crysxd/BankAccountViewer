//
// Copyright (c) 2013 figo GmbH
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.
//

package me.figo.internal;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.X509TrustManager;

public class FigoTrustManager implements X509TrustManager {

    private static final List<String> VALID_FINGERPRINTS = new ArrayList<String>(Arrays.asList(
            "3A62544D86B43438EA34644E9510A9FF372769C0",                                                     
            "CFC1BC7F6A16092B10838AB0224F3A65D270D73E"));

    /**
     * @return the list of trusted certificate fingerprints using SHA1
     */
    public static List<String> getTrustedFingerprints() {
        return VALID_FINGERPRINTS;
    }
    
    /**
     * Add a fingerprint to the trusted list, e.g. when using a custom figo deployment.
     * 
     * @param fingerprint the SHA1 hash of the SSL certificate in upper case
     */
    public static void addTrustedFingerprint(String fingerprint) {
        VALID_FINGERPRINTS.add(fingerprint);
    }
    
    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

    @Override
    public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
        if (certs.length == 0) {
            throw new CertificateException("No certificate found");
        } else {
            String thumbprint = getThumbPrint(certs[0]);
            if (!VALID_FINGERPRINTS.contains(thumbprint))
                throw new CertificateException();
        }
    }

    private static String getThumbPrint(X509Certificate cert) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] der = cert.getEncoded();
            md.update(der);
            byte[] digest = md.digest();
            return new String(bytesToHex(digest));
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (CertificateEncodingException e) {
            return "";
        }
    }

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
