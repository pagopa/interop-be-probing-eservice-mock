package it.pagopa.interop.probing.eservice.mock.webservice.util;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import io.jsonwebtoken.Jwts;
import java.util.logging.Logger;

public class SecurityUtil {

  private static final Logger logger = Logger.getLogger(SecurityUtil.class.getName());

  public static boolean checkJwtValid(String authorizationHeader, String pKey) {

    try {
      String publicKeyPEMTrimmed = pKey.replace("-----BEGIN PUBLIC KEY-----", "")
          .replace("-----END PUBLIC KEY-----", "").replaceAll("\\s+", "");

      String authTrimmed = authorizationHeader.replace("Bearer ", "");

      byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyPEMTrimmed);

      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);

      KeyFactory keyFactory = KeyFactory.getInstance("RSA");

      PublicKey publicKey = keyFactory.generatePublic(keySpec);

      Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(authTrimmed);
    } catch (Exception e) {
      logger.warning("Exception: " + e.getMessage());
      return false;
    }
    return true;
  }

}
