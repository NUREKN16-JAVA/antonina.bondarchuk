package ua.nure.kn.bondarchuk.usermanagement2.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

import ua.nure.kn.bondarchuk.usermanagement2.User;

class HsqldbUserDao implements UserDao {

	private static final String SELECT_ALL_QUERY = "SELECT id, firstname, lastname, dateofbirth FROM users";
	private static final String INSERT_QUERY = "INSERT INTO users (firstname, lastname, dateofbirth) VALUES (?, ?, ?)";
	private static final String FIND_QUERY = "SELECT * FROM users WHERE id = ?";
	private static final String DELETE_QUERY = "DELETE FROM users WHERE id = ?";
	private static final String UPDATE_QUERY = "UPDATE users SET firstname = ?, lastname = ?, dateofbirth = ?  WHERE id = ?";
	private ConnectionFactory connectionFactory;
	
	public HsqldbUserDao() {
	}
	
	public HsqldbUserDao(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	

	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}
	
	@Override
	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}


	@Override
	public User create(User user) throws DatabaseException {
		
		try {
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection
					.prepareStatement(INSERT_QUERY);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setDate(3, new Date(user.getDateOfBirth().getTime()));
			int n = statement.executeUpdate(); //when we change
			if (n != 1) {
				throw new DatabaseException("Number of the inserted rows: " + n);
			}
			CallableStatement callableStatement = connection.prepareCall("call IDENTITY()");
			
			ResultSet keys = callableStatement.executeQuery();
			if (keys.next()) {
				user.setId(new Long(keys.getLong(1)));
			}
			keys.close();
			callableStatement.close();
			statement.close();
			connection.close();
			return user;
			
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
		
	}

	@Override
	public User find(Long id) throws DatabaseException {
	     
	        try {
	        	Connection connection = connectionFactory.createConnection();
	        	PreparedStatement statement = connection
						.prepareStatement(FIND_QUERY);
	            statement.setLong(1, id);
	            ResultSet resultSet = statement.executeQuery();
	            if (resultSet.next()) {
	            	long usrid = resultSet.getLong(1);
					String firstName = resultSet.getString(2);
				    String lastName = resultSet.getString(3);
				    Date dateOfBirth = resultSet.getDate(4);
				    resultSet.close();
					statement.close();
					connection.close();
				    return new User(usrid, firstName, lastName, dateOfBirth);
	            }
	            resultSet.close();
				statement.close();
				connection.close();
	            throw new DatabaseException("Can not find user by id: " + id);
	        } catch (SQLException e) {
	            throw new DatabaseException(e.getMessage());
	        } 
		
	}
	
	 	
	

	@Override
	public Collection<User> findAll() throws DatabaseException {
		Collection result = new LinkedList<>();
		
		
		try {
			Connection connection = connectionFactory.createConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);
			while(resultSet.next()) {
				User user = new User();
				user.setId(new Long(resultSet.getLong(1)));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				user.setDateOfBirth(resultSet.getDate(4));
				result.add(user);
			}
		} catch (DatabaseException e) {
			throw e;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		return result;
		
	}

	@Override
	public void update(User user) throws DatabaseException {
		try {
        	Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setDate(3, new Date(user.getDateOfBirth().getTime()));
            statement.setLong(4, user.getId());
            int updatedRows = statement.executeUpdate();
            if (updatedRows != 1) {
                throw new DatabaseException("Updated rows: " + updatedRows);
            }
            statement.close();
			connection.close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

	}

	@Override
	public void delete(User user) throws DatabaseException {
		 
        try {
        	Connection connection = connectionFactory.createConnection();
        	PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
            statement.setLong(1, user.getId());
            int n = statement.executeUpdate();
            if (n != 1) {
                throw new DatabaseException("Exception while delete operation. Effected rows: " + n);
            }
            statement.close();
			connection.close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

	}

	

}
