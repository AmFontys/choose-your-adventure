package nl.chooseyouradventure.security.encoding;

import nl.chooseyouradventure.model.AccessToken;


public interface AccessTokenEncoder {
    String encode(AccessToken accessToken);
}
