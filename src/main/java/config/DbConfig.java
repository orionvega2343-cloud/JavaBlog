package config;

public class DbConfig
{
    private String dbName;
    private String dbUser;
    private String dbHost;
    private int dbPort;
    private String dbSslMode;

    public String getDbName()
    {
        return dbName;
    }

    public String getDbUser()
    {
        return dbUser;
    }

    public String getDbHost()
    {
        return dbHost;
    }

    public int getDbPort()
    {
        return dbPort;
    }

    public String getDbSslMode()
    {
        return dbSslMode;
    }
}