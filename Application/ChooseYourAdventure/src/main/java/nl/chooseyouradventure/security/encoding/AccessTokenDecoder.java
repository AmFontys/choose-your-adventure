package nl.chooseyouradventure.security.encoding;

import nl.chooseyouradventure.model.AccessToken;


public interface AccessTokenDecoder    {
    AccessToken decode(String accessTokenEncoded);
}
