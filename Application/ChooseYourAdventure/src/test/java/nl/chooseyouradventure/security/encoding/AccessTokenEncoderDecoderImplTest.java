package nl.chooseyouradventure.security.encoding;

import nl.chooseyouradventure.model.dta.AccessToken;
import nl.chooseyouradventure.security.authentication.DatabaseUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

class AccessTokenEncoderDecoderImplTest {

    @Mock
    AccessTokenEncoderDecoderImpl accessTokenEncoderDecoder;

    @InjectMocks
    DatabaseUserDetailsService service;

    @BeforeEach
    void setup(){
        accessTokenEncoderDecoder= new AccessTokenEncoderDecoderImpl("secretKeyWhichIsSoSecretYouDontKnowWhatsInsideOfit",service);
    }

    private String encodeForDecode(AccessToken token){
        return accessTokenEncoderDecoder.encode(token);
    }

    @Test
    void encode() {
        List<String> roleList = new ArrayList<>();
        roleList.add("ROLE_USER");
        roleList.add("ROLE_MOD");

        AccessToken madeToken = AccessToken.builder()
                .userId(100)
                .roles(roleList)
                .subject("something")
                .build();

        String returned= accessTokenEncoderDecoder.encode(madeToken);

        assertThat(returned.getBytes(StandardCharsets.UTF_8).length).isEqualTo(197);

    }

    @Test
    void encode_EmptyRoles() {
        List<String> roleList = new ArrayList<>();

        AccessToken madeToken = AccessToken.builder()
                .userId(100)
                .roles(roleList)
                .subject("something")
                .build();

        String returned= accessTokenEncoderDecoder.encode(madeToken);

        assertThat(returned.getBytes(StandardCharsets.UTF_8).length).isEqualTo(153);

    }

    @Test
    void encode_EmptyUserId() {
        List<String> roleList = new ArrayList<>();
        roleList.add("ROLE_USER");
        roleList.add("ROLE_MOD");

        AccessToken madeToken = AccessToken.builder()
                .roles(roleList)
                .subject("something")
                .build();

        String returned= accessTokenEncoderDecoder.encode(madeToken);

        assertThat(returned.getBytes(StandardCharsets.UTF_8).length).isEqualTo(180);

    }

    @Test
    void decode() {
        List<String> roleList = new ArrayList<>();
        roleList.add("ROLE_USER");
        roleList.add("ROLE_MOD");

        AccessToken expectedDecoded = AccessToken.builder()
                .userId(100)
                .roles(roleList)
                .subject("something")
                .build();

        String accesTokenEncoded = encodeForDecode(expectedDecoded);

        AccessToken actualDecoded = accessTokenEncoderDecoder.decode(accesTokenEncoded);

        assertThat(actualDecoded).isEqualTo(expectedDecoded);

    }

    @Test()
    void decode_expired() {
        String accesTokenEncoded = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzb21ldGhpbmciLCJpYXQiOjE2NzM2MTM3MzYsImV4cCI6MTY3MzYxNDMzNiwicm9sZXMiOlsiUk9MRV9VU0VSIiwiUk9MRV9NT0QiXSwidXNlcmlkIjoxMDB9.VpK58getOCp75O92EoNKgc9Z1A66nIMezhQF8SwlFXk";

        assertThrows(InvalidAccessTokenException.class, () -> accessTokenEncoderDecoder.decode(accesTokenEncoded));
    }

    @Test
    void getExpirationDateFromToken() {
        List<String> roleList = new ArrayList<>();
        roleList.add("ROLE_USER");
        roleList.add("ROLE_MOD");

        AccessToken expectedDecoded = AccessToken.builder()
                .userId(100)
                .roles(roleList)
                .subject("something")
                .build();

        String accesTokenEncoded = encodeForDecode(expectedDecoded);
        Date expectedDate =new Date("01/13/2023");

        Date actualDate = accessTokenEncoderDecoder.getExpirationDateFromToken(accesTokenEncoded);

        assertThat(actualDate.getYear()).isEqualTo(expectedDate.getYear());
        assertThat(actualDate.getMonth()).isEqualTo(expectedDate.getMonth());
    }

    @Test
    void getClaimFromToken() {
    }

    @Test
    void getUsernameFromToken() {
        List<String> roleList = new ArrayList<>();
        roleList.add("ROLE_USER");
        roleList.add("ROLE_MOD");

        AccessToken expectedDecoded = AccessToken.builder()
                .userId(100)
                .roles(roleList)
                .subject("something")
                .build();

        String accesTokenEncoded = encodeForDecode(expectedDecoded);
        String expectedUser="something";

        String actualUser= accessTokenEncoderDecoder.getUsernameFromToken(accesTokenEncoded);
        assertThat(actualUser).isEqualTo(expectedUser);
    }
}