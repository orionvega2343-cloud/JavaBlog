package config;
import io.github.cdimascio.dotenv.Dotenv;
public class DbConfig
{
    private String dbName;
    private String dbUser;
    private String dbHost;
    private int dbPort;
    private String dbSslMode;
    private String dbPassword = Dotenv.load().get("DB_PASS");

    // Геттеры
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

    public String getDbPassword()
    {
        return dbPassword;
    }

    // Сеттеры
    public void setDbName(String dbName)
    {
        this.dbName = dbName;
    }

    public void setDbUser(String dbUser)
    {
        this.dbUser = dbUser;
    }

    public void setDbHost(String dbHost)
    {
        this.dbHost = dbHost;
    }

    public void setDbPort(int dbPort)
    {
        this.dbPort = dbPort;
    }

    public void setDbSslMode(String dbSslMode)
    {
        this.dbSslMode = dbSslMode;
    }



}