
package com.example.gestionAchat.config;

import java.util.Base64;
import java.util.List;

public class Device {

	public Boolean valid(List l1, List l) {
		if (l1 != null) {
			for (int i = 0; i < l1.size(); ++i) {
				for (int j = 0; j < l.size(); ++j) {
					if (l1.get(i).equals(l.get(j))) {
						return true;
					}
				}
			}
			return false;
		}
		return true;
	}

	public String DecodeJWT( String jwtToken) {
		Base64.Decoder decoder = Base64.getUrlDecoder();
		String[] split_string = jwtToken.split("\\.");
		String base64EncodedBody = split_string[1];
		String body = new String(decoder.decode(base64EncodedBody));
		return body;
	}

}
