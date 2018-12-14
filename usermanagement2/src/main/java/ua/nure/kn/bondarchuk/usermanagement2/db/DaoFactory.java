package ua.nure.kn.bondarchuk.usermanagement2.db;

import java.io.IOException;
import java.util.Properties;

public abstract class DaoFactory {
	
	private static final String DAO_FACTORY = "dao.factory";
	protected static final String USER_DAO = "dao.ua.nure.kn.bondarchuk.usermanagement2.db.UserDao";
	protected static Properties properties;
	
	
	static {
		properties = new Properties();
		try {
			properties.load(DaoFactory.class.getClassLoader().getResourceAsStream("settings.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static DaoFactory instance;
	
	public static DaoFactory getInstance() {
		if(instance == null) {
			Class<?> factoryClass;
			try {
				factoryClass = Class.forName(properties.getProperty(DAO_FACTORY));
				instance = (DaoFactory) factoryClass.newInstance();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return instance;
	}
	
	protected DaoFactory() {
		
	}
	
	protected ConnectionFactory getConnectionFactory() {
		
		return new ConnectionFactoryImpl(properties);
	}
	
	public abstract UserDao getUserDao();
	
	
	public static void init(Properties prop) {
		properties = prop;
		instance = null;
		
	}

}
