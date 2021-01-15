/*
	Name: Drew King
	Course: CNT 4714 -- FALL 2019 -- Project Four
	Assignment Title: A Three-Tier Distributed Web-Based Application
	Date: December 1, 2019
*/

import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.Connection;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;

public class buisnesslogic extends HttpServlet{
	private Connection connection;
	private Statement statement;

	public void init(ServletConfig config) throws ServletException{
		try{
			super.init(config);
			Class.forName(config.getInitParameter("databaseDriver"));
			connection = DriverManager.getConnection(config.getInitParameter("databaseName"), config.getInitParameter("username"), config.getInitParameter("password"));
			statement = connection.createStatement();
		}//end try
		catch(Exception exception){
			exception.printStackTrace();
			throw new UnavailableException(exception.getMessage());
		}//end catch
	} //end init

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String sqlStatement = request.getParameter("sqlStatement").toLowerCase();
		String message = null;
		
		//determining if this is a select statement
		if(sqlStatement.contains("select")){
			try{
				String query;
				ResultSet resultSet = statement.executeQuery(sqlStatement);
				ResultSetMetaData metaData = resultSet.getMetaData();
				int numberOfColumns = metaData.getColumnCount();
				//creating table
				String div = "<div style=text-align:center;><table style=border:1px solid black;border-collapse:collapse; padding: 10px;>";
				String col = "<thead><tr>";
				for(int i = 1; i <=numberOfColumns; i++){
					col += "<th style=border:1px solid black;border-collapse:collapse; padding: 10px;>" + metaData.getColumnName(i) + "</th>";
				}
				col += "</tr></thead>";

				String info = "<tbody>";
				while(resultSet.next()){
					info += "<tr>";
					for(int i = 1; i <= numberOfColumns; i++){
						if(i == 1){
							info += "<td style=border:1px solid black;border-collapse:collapse; padding: 10px;>" + resultSet.getString(i) + "</th>";
						}else{
							info += "<td style=border:1px solid black;border-collapse:collapse; padding: 10px;>" + resultSet.getString(i) + "</th>";
						}

					}
					info += "</tr>";
				}
				info += "</tbody>";
				String close = "</table></div>";


				message = div + col + info + close;
			} catch(SQLException exception){
				message = exception.getMessage();
				exception.printStackTrace();
			}//end catch
		}//end if
		else{
			try{
				int numberofRows = 0;

				ResultSet resultSetBefore = statement.executeQuery("select * from shipments where quantity >= 100");
				resultSetBefore.last();
				int numberOfQualifiedShipments = resultSetBefore.getRow();

				statement.executeUpdate("CREATE TABLE beforeShipments like shipments");
				statement.executeUpdate("INSERT INTO beforeShipments select * from shipments");
				numberofRows = statement.executeUpdate(sqlStatement);

				message = "<div> Succesfully Executed.</div>";

				ResultSet resultSetAfter = statement.executeQuery("select * from shipments where quantity >= 100");
				resultSetAfter.last();
				int numberOfQualifiedShipment = resultSetAfter.getRow();

				int numberOfStatusUpdates = statement.executeUpdate("update suppliers set status = status + 5 where snum in (select distinct snum from shipments left join beforeShipments using (snum,pnum,jnum,quantity) where beforeShipments.snum is null and quantity > 100);");
				statement.executeUpdate("DROP TABLE beforeShipments");
			}catch(SQLException exception1){
				message = exception1.getMessage();
				exception1.printStackTrace();
			}//end catch
		}//end else

		HttpSession session = request.getSession();
		session.setAttribute("message", message);
		session.setAttribute("sqlStatement", sqlStatement);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);

	}//end doPost
}
