package ua.nure.kn.bondarchuk.usermanagement2.db;

import java.sql.Connection;

public interface ConnectionFactory {
	
	Connection createConnection() throws DatabaseException;

}
