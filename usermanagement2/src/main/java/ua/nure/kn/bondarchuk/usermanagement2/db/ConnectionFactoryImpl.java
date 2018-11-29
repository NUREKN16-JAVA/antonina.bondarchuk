package ua.nure.kn.bondarchuk.usermanagement2.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactoryImpl implements ConnectionFactory {
	
	//link for our DB (protocol use hsqldb:we will save it in file:directory)
	private static final String DRIVER = "org.hsqldb.jdbcDriver";
    private static final String URL = "jdbc:hsqldb:file:db/usermanagement2";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private String driver;
    private String url;
    private String user;
    private String password;

    public ConnectionFactoryImpl() {
        this.driver = DRIVER;
        this.url = URL;
        this.user = USER;
        this.password = PASSWORD;
    }

    public ConnectionFactoryImpl(String driver, String url, String user, String password) {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;
    }

	@Override
	public Connection createConnection() throws DatabaseException {
		
		
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new DatabaseException(e); //если не найден класс драйвера, то ошибка
		}
		
		try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

}
