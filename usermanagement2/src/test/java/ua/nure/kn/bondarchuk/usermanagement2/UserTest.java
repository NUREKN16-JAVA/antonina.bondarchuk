package ua.nure.kn.bondarchuk.usermanagement2;


import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

public class UserTest extends TestCase {
	
	
	private User user;
	private Date dateOfBirth;
	
	
	private static final int CURRENT_YEAR = 2018;
	private static final int YEAR_OF_BIRTH = 1999;
	
	private static final String FULL_NAME_ETALON = "Bondarchuk, Tonya";
	private static final String LAST_NAME_ETALON = "Bondarchuk";
	private static final String FIRST_NAME_ETALON = "Tonya";

	
	//test are actual for 22/11/2018
	    //if bday was in this year
		private static final int ETALONE_AGE_1 = 19;
		private static final int DAY_OF_BIRTH_1 = 15;
		private static final int MONTH_OF_BIRTH_1 = 6;
		
		//if bday is today
		private static final int ETALONE_AGE_2 = 19;
		private static final int DAY_OF_BIRTH_2 = 22;
		private static final int MONTH_OF_BIRTH_2 = 10;//counts months from 0
		
		//if bday is tomorrow
		private static final int ETALONE_AGE_3 = 18;
		private static final int DAY_OF_BIRTH_3 = 23;
		private static final int MONTH_OF_BIRTH_3 = 10;//counts months from 0
		
		//if bday is on 31.12.1999
		private static final int ETALONE_AGE_4 = 18;
		private static final int DAY_OF_BIRTH_4 = 31;
		private static final int MONTH_OF_BIRTH_4 = 11;//counts months from 0
		
		//if bday is on 1.01.2000
		private static final int ETALONE_AGE_5 = 18;
		private static final int DAY_OF_BIRTH_5 = 1;
		private static final int MONTH_OF_BIRTH_5 = 0;//counts months from 0
		
		//if bday is on 29.02.2000
		private static final int YEAR_OF_BIRTH_2 = 2000;
		private static final int ETALONE_AGE_6 = 18;
		private static final int DAY_OF_BIRTH_6 = 29;
		private static final int MONTH_OF_BIRTH_6 = 1;//counts months from 0
	
	public UserTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		user = new User();
		
		Calendar calendar = Calendar.getInstance();
		dateOfBirth = calendar.getTime();
	}
	
	public void testGetFullName() {
		user.setFirstName(FIRST_NAME_ETALON);
		user.setLastName(LAST_NAME_ETALON);
		assertEquals(FULL_NAME_ETALON, user.getFullName());
	}
	
	public void testGetAge() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH_1, DAY_OF_BIRTH_1);
		user.setDateOfBirth(calendar.getTime());
		assertEquals(ETALONE_AGE_1, user.getAge());
	}
	
	public void testGetAgeBdayToday() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH_2, DAY_OF_BIRTH_2);
		user.setDateOfBirth(calendar.getTime());
		assertEquals(ETALONE_AGE_2, user.getAge());
	}
	
	public void testGetAgeBdayTomorrow() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH_3, DAY_OF_BIRTH_3);
		user.setDateOfBirth(calendar.getTime());
		assertEquals(ETALONE_AGE_3, user.getAge());
	}
	
	public void testGetAgeBdayNewYearEve() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH_4, DAY_OF_BIRTH_4);
		user.setDateOfBirth(calendar.getTime());
		assertEquals(ETALONE_AGE_4, user.getAge());
	}
	
	public void testGetAgeBdayFirstOfJanuary() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(YEAR_OF_BIRTH_2, MONTH_OF_BIRTH_5, DAY_OF_BIRTH_5);
		user.setDateOfBirth(calendar.getTime());
		assertEquals(ETALONE_AGE_5, user.getAge());
	}
	
	public void testGetAgeBday29OfFebruary() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(YEAR_OF_BIRTH_2, MONTH_OF_BIRTH_6, DAY_OF_BIRTH_6);
		user.setDateOfBirth(calendar.getTime());
		assertEquals(ETALONE_AGE_6, user.getAge());
	}
	

}
