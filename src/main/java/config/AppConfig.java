package config;

// AppConfig - собирает DbConfig и Jwt конфиг в 1 целое с помощью геттеров

public class AppConfig
{
    private DbConfig db;
    private JwtConfig jwt;


    public DbConfig getDb() {
        return db;
    }
    public JwtConfig getJwt()
    {
        return jwt;
    }

    public void setDb(DbConfig db) {
        this.db = db;
    }
}




// TODO: собрать конструкторы для сборки final