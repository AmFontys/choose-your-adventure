package nl.chooseyouradventure.security.encoding;

import nl.chooseyouradventure.model.dta.AccessToken;


public interface AccessTokenDecoder    {
    AccessToken decode(String accessTokenEncoded);
}
