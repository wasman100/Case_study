package com.perscholas.first_servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.perscholas.home_insurance.DAO.CoverageDetailsDAO;
import com.perscholas.home_insurance.DAO.HomeInfoDAO;
import com.perscholas.home_insurance.DAO.HomeOwnerDAO;
import com.perscholas.home_insurance.DAO.UserDAO;
import com.perscholas.home_insurance.DAO.property_infoDAO;
import com.perscholas.home_insurance.models.CoverageDetails;
import com.perscholas.home_insurance.models.HomeInfo;
import com.perscholas.home_insurance.models.HomeOwner;
import com.perscholas.home_insurance.models.Users;
import com.perscholas.home_insurance.models.property_info;

/**
 * Servlet implementation class First_try
 */
@WebServlet(urlPatterns = { "/", "/index" })
public class index extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String message = "";
	List<String> errors = new ArrayList<String>();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public index() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getServletPath();
		try {
			switch (action) {
			case "/register":
				register(request, response);
				break;
			case "/registerUser":
				registerUser(request, response);
				break;
			case "/login":
				loginUser(request, response);
				break;
			case "/home":
				home(request, response);
				break;
			case "/logout":
				logout(request, response);
				break;
			case "/get quote":
				get_quote(request, response);
				break;
			case "/saveHomeInfo":
				saveHomeInfo(request, response);
				break;
			case "/homeOwnerInfo":
				homeOwnerInfo(request,response);
				break;
			case "/propertyInfo":
				propertyInfo(request,response);
				break;
			case "/saveOwnerInfo":
				saveOwnerInfo(request,response);
				break;
			case "/savePropertyInfo":
				savePropertyInfo(request,response);
				break;
			case "/calculateCoverageDetails":
				calculateCoverageDetails(request,response);
				break;
			case "/saveCoverageDetails":
			saveCoverageDetails(request,response);
			break;
			case "/DetailsPolicy":
				DetailsPolicy(request,response);
				break;
			default:
				RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
				rd.forward(request, response);
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	/*****************************************
	 * going to register page
	 *************************/
	private void register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
		rd.forward(request, response);
	}

	/*****************************************
	 * saving user info to database
	 * 
	 * @throws ServletException
	 *************************/
	private void registerUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String password_confirm = request.getParameter("password_confirm");

		errors.clear();

		if (username.equals("") || username.length() < 3) {
			errors.add("Name can not be empty and must have more than 2 characters");

		}
		if (!password.equals(password_confirm)) {
			errors.add("passwords do not match.");
		}
		if (!errors.isEmpty()) {
			request.setAttribute("errors", errors);
			RequestDispatcher rd = request.getRequestDispatcher("/newUsersform");
			rd.forward(request, response);
		} else {
			Users newuser = new Users(username, password);
			UserDAO u_dao = new UserDAO();
			int returnId = u_dao.registerUser(newuser);
			System.out.println(returnId);
			response.sendRedirect("main");
		}
	}

	/*****************************************
	 * Login and validation
	 * 
	 * @throws ServletException
	 *************************/
	private void loginUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {

		HttpSession session = request.getSession();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		errors.clear();
		// login validation
		if (username.isEmpty()) {
			errors.add("Please enter a valid username");
		}
		if (password.isEmpty()) {
			errors.add("please enter a password");
		}
		if (!errors.isEmpty()) {
			request.setAttribute("errors", errors);
			RequestDispatcher rd = request.getRequestDispatcher("/main");
			rd.forward(request, response);
		} else {
			Users u = new Users();
			u.setUsername(username);
			UserDAO dao = new UserDAO();
			Users d = dao.loginUsers(u.getUsername());

			if (d.getUsername() == null) {
				message = "User name not found. Please try again or register.";
				session.setAttribute("currentUser", null);
				request.setAttribute("validation", message);
				RequestDispatcher rd = request.getRequestDispatcher("main");
				rd.forward(request, response);
			}
			if (d.getPassword().equals(password)) {
				if(d.getUser_role().equals(1)) {
					session.setAttribute("currentUser", d);
					RequestDispatcher rd = request.getRequestDispatcher("Admin.jsp");
					rd.forward(request, response);
				}else {
					session.setAttribute("currentUser", d);
					response.sendRedirect("home");
				}
				
			} else {
				message = "invalid login.";
				session.setAttribute("currentUser", null);
				request.setAttribute("validation", message);
				RequestDispatcher rd = request.getRequestDispatcher("main");
				rd.forward(request, response);
			}
		}

	}

	/*****************************************
	 * home page
	 * 
	 * @throws ServletException
	 *************************/
	private void home(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		HttpSession session = request.getSession();
		if (session.getAttribute("currentUser") != null) {
			RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
			rd.forward(request, response);

		} else {
			response.sendRedirect("main");
		}
	}

	/*****************************************
	 * Logout form
	 *************************/
	private void logout(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		response.sendRedirect("main");
	}

	/****************** get a Quote ********************/
	private void get_quote(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		HttpSession session = request.getSession();
		if (session.getAttribute("currentUser") != null) {
			RequestDispatcher rd = request.getRequestDispatcher("get_quote.jsp");
			rd.forward(request, response);

		} else {
			response.sendRedirect("main");
		}

	}

	/******************** send get quote info ************/
	private void saveHomeInfo(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		HttpSession session = request.getSession();
		if(session.getAttribute("currentUser")!= null) {
		
		String address = request.getParameter("address");
		String state = request.getParameter("state");
		String city = request.getParameter("city");
		String zip = request.getParameter("zip");
		String resdience_type = request.getParameter("Residence_type");
		String resdience_use = request.getParameter("use");
		Users u = (Users)session.getAttribute("currentUser");
		int user_id = u.getUser_id();
		String address_line_2 =request.getParameter("address_line_2");
		
		System.out.println(address);
		errors.clear();
		HomeInfo home = new HomeInfo(address,state,city,zip,resdience_type,resdience_use,user_id,address_line_2);
		System.out.println("After contructor: " + home.getAddress());
		HomeInfoDAO h_dao = new HomeInfoDAO();
		h_dao.StoreHomeInfo(home);
		response.sendRedirect("homeOwnerInfo");
		}
	}

	/*
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 * response)
	 * home owner page 
	 */

	private void homeOwnerInfo(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		HttpSession session = request.getSession();
		if (session.getAttribute("currentUser") != null) {
			RequestDispatcher rd = request.getRequestDispatcher("home_info.jsp");
			rd.forward(request, response);

		} else {
			response.sendRedirect("main");
		}

	}
	
	/********************************************property info page****************************************************************/
	private void propertyInfo(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		HttpSession session = request.getSession();
		if (session.getAttribute("currentUser") != null) {
			RequestDispatcher rd = request.getRequestDispatcher("propierty_details.jsp");
			rd.forward(request, response);

		} else {
			response.sendRedirect("main");
		}
		
	}
	/*********************************************************save owner info*****************************************************/
	private void saveOwnerInfo(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		HttpSession session = request.getSession();
		session.setAttribute("User", HomeOwner.getU_id());
		if(session.getAttribute("currentUser")!=null) {
		
		String first_name = request.getParameter("first_name");
		String last_name = request.getParameter("last_name");
		String retiered = request.getParameter("retiered");
		String ssn = request.getParameter("ssn");
		String email = request.getParameter("email");
		String birth = request.getParameter("birth");
		Users u = (Users)session.getAttribute("currentUser");
		int user_id = u.getUser_id();

		
		HomeOwner ho = new HomeOwner(first_name,last_name,retiered,ssn,email,birth,user_id);
		HomeOwnerDAO ho_DAO=new HomeOwnerDAO();
	ho_DAO.StoreHomeOwner(ho);
	response.sendRedirect("propertyInfo");
		}
	}
	
	/************************************************saving property info*******************************************************/
private void savePropertyInfo(HttpServletRequest request, HttpServletResponse response)
		throws SQLException, IOException, ServletException {
	HttpSession session = request.getSession();
	String value = request.getParameter("value");
	String built = request.getParameter("built");
	String footage = request.getParameter("footage");
	String dwelling = request.getParameter("dwelling");
	String roof = request.getParameter("roof");
	String halfBaths = request.getParameter("halfBaths");
	String baths = request.getParameter("baths");
	String pool = request.getParameter("pool");
	String garage = request.getParameter("garage");
	
	
	property_info pi = new property_info(value,built,footage,dwelling,roof,halfBaths,baths,pool,garage);
	session.setAttribute("currentPropertyInfo", pi);
	property_infoDAO pi_DAO = new property_infoDAO();
	pi_DAO.Storepropertyinfo(pi);
response.sendRedirect("calculateCoverageDetails");
}




/*********************************************************************caluculations for policy********************************************************/

private void calculateCoverageDetails(HttpServletRequest request, HttpServletResponse response)
		throws SQLException, IOException, ServletException {
	HttpSession session = request.getSession();
	property_info currentProperty = (property_info) session.getAttribute("currentPropertyInfo");
	int value =  Integer.parseInt(currentProperty.getValue());
	int year = Integer.parseInt(currentProperty.getYear());
	int footage = Integer.parseInt(currentProperty.getFootage());
	
	int marketValue = footage*120;
	
	int ALC = 5000;
	int deductable = (int) (.01*marketValue);
	int currentYear = Calendar.getInstance().get(Calendar.YEAR);
	int difference = year-currentYear;
	int reduction=0;
	if(difference<5) {
		reduction =(int) (.1*marketValue);
	}else if(difference<10){
		reduction = (int) (.2*marketValue);
	}else if(difference<20) {
		reduction = (int) (.3*marketValue);
	}else if(difference>20) {
		reduction=(int) (.5*marketValue);
	}
	int dweleingCovereage = (int) (.50*value + reduction);
	int rate = (int) (dweleingCovereage/200);
	int detacheStructure = (int) (.10*dweleingCovereage);
	int personalProperty = (int) (.60*dweleingCovereage);
	int living = (int) (.20*dweleingCovereage);
	int premium1 = rate*marketValue;
String message = currentProperty.getDwelling();
int premium2=0;
switch(message){
case "single-family":
	 premium2 = (int) (premium1*.0005);
	break;
case "condo":
case "duplex":
case "apartment":
 premium2 = (int) (premium1*.0006);
 break;
case "townhouse":
case "rowhouse":
premium2 = (int) (premium1*.0007);
	break;
}
int premium = premium1+premium2;
int mothlyPremium=premium/12;
session.setAttribute("monthlyP", mothlyPremium);
session.setAttribute("premium", premium);
session.setAttribute("coverage", dweleingCovereage);
session.setAttribute("detacheStructure", detacheStructure);
session.setAttribute("personalProperty", personalProperty);
session.setAttribute("living", living);
session.setAttribute("ALC", ALC);
session.setAttribute("deductable", deductable);

RequestDispatcher rd = request.getRequestDispatcher("CoverageDetails.jsp");
rd.forward(request, response);

}

/********************************************saving coverage info*************************************************/
private void saveCoverageDetails(HttpServletRequest request, HttpServletResponse response)
		throws SQLException, IOException, ServletException {
	HttpSession session = request.getSession();
	
	Object monthlyP =  session.getAttribute("monthlyP");
	Object coverage = session.getAttribute("coverage");
	Object detachedStructors = session.getAttribute("detacheStructure");
	Object personalProperty = session.getAttribute("personalProperty");
	Object living = session.getAttribute("living");
	Object ALC = session.getAttribute("ALC");
	Object deductible = session.getAttribute("deductable");	


	
	CoverageDetails NCD = new CoverageDetails(monthlyP,coverage,detachedStructors,
			personalProperty,living, ALC,deductible); 
	CoverageDetailsDAO CDDAO = new CoverageDetailsDAO();
	CDDAO.StoreCoverageDetails(NCD);
	
	response.sendRedirect("DetailsPolicy");

	
}
/********************************* details of policy************************************************************************/
private void DetailsPolicy(HttpServletRequest request, HttpServletResponse response)
		throws SQLException, IOException, ServletException {
	HttpSession session = request.getSession();
	
	RequestDispatcher rd = request.getRequestDispatcher("details.jsp");
	rd.forward(request, response);

}


}
