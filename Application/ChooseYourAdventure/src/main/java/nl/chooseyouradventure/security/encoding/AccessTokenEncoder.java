package nl.chooseyouradventure.security.encoding;

import nl.chooseyouradventure.model.dta.AccessToken;


public interface AccessTokenEncoder {
    String encode(AccessToken accessToken);
}
