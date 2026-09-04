package config;

public class JwtConfig
{
    private final String jwtAccessToken = System.getenv("ACCESS_TOKEN");

    // сеттер - для возможности чтения поля другими классами,
    // изменение полностью запрещено
    public String getJwtAccessToken()
    {
        return jwtAccessToken;
    }

}
