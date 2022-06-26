package cs601.utilities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitTests {

    @Test
    public void TestGenerateNonce() {
        assertEquals("09fb52c3ff2b12e35e720115e16c41d14ea61e67",
                LoginUtilities.generateNonce("1jaox1h1cube81kpgwmobg4kls"));

    }

    @Test
    public void TestGenerateSlackAuthorizeURL() {
        assertEquals("https://slack.com/openid/connect/authorize?response_type=code&scope=openid%20profile%20email&client_id=1234&state=abcd&nonce=123456&redirect_uri=https://google.com",
                LoginUtilities.generateSlackAuthorizeURL("1234", "abcd", "123456", "https://google.com"));
    }

    @Test
    public void TestGenerateSlackTokenURL() {
        assertEquals("https://slack.com/api/openid.connect.token?client_id=12345&client_secret=abcd&code=123456&redirect_uri=https://google.com",
                LoginUtilities.generateSlackTokenURL("12345", "abcd", "123456", "https://google.com"));
    }




}
