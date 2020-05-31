package com.shoobyman.sireatsalot.Utils;

import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class NetworkUtils {

    private final String TAG = this.getClass().getSimpleName();
    private static final String PARAM_METHOD_VALUE = "GET";
    private static final String PARAM_KEY_VALUE = "";
    private static final String PARAM_SECRET_VALUE = "" + "&";
    private static final String BASE_URL = "https://platform.fatsecret.com/rest/server.api";
    private static final String ENCODE_ALGORITHM = "HmacSHA1";


    public static String getUrl() {
        List<String> params = new ArrayList<>(Arrays.asList(generateOauthParams()));
        String[] template = new String[1];
        params.add("method=food.get");
        params.add("food_id=4384");
        params.add("oauth_signature=" + sign(PARAM_METHOD_VALUE, BASE_URL, params.toArray(template)));
        String url = BASE_URL + "?" + paramify(params.toArray(template));
        return url;
    }

    private static String[] generateOauthParams() {
        return new String[]{
                "oauth_consumer_key=" + PARAM_KEY_VALUE,
                "oauth_signature_method=HMAC-SHA1",
                "oauth_timestamp=" + Long.valueOf(System.currentTimeMillis() * 2).toString(),
                "oauth_nonce=" + nonce(),
                "oauth_version=1.0",
                "format=json"};
    }

    private static String sign(String method, String uri, String[] params) {
        String[] p = {method, Uri.encode(uri), Uri.encode(paramify(params))};
        String s = join(p, "&");
        SecretKey sk = new SecretKeySpec(PARAM_SECRET_VALUE.getBytes(), ENCODE_ALGORITHM);
        try {
            Mac m = Mac.getInstance(ENCODE_ALGORITHM);
            m.init(sk);
            return Uri.encode(new String(Base64.encode(m.doFinal(s.getBytes()), Base64.DEFAULT)).trim());
        } catch (java.security.NoSuchAlgorithmException e) {
            Log.w("FatSecret_TEST FAIL", e.getMessage());
            return null;
        } catch (java.security.InvalidKeyException e) {
            Log.w("FatSecret_TEST FAIL", e.getMessage());
            return null;
        }
    }

    private static String nonce() {
        Random r = new Random();
        StringBuilder n = new StringBuilder();
        for (int i = 0; i < r.nextInt(8) + 2; i++)
            n.append(r.nextInt(26) + 'a');
        return n.toString();
    }

    private static String paramify(String[] params) {
        String[] p = Arrays.copyOf(params, params.length);
        Arrays.sort(p);
        return join(p, "&");
    }

    private static String join(String[] array, String separator) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                b.append(separator);
            }
            b.append(array[i]);
        }
        return b.toString();
    }
}
