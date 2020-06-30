import java.util.Scanner;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import com.google.gson.*;

import okhttp3.*;


public class Using_CRUD_Operations {
	
	public static void main(String[] args) throws IOException {
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		boolean finished = false;
		while (!finished)
		{
			System.out.println("Please type in the number for what you would like to do:");
			System.out.println("1. Retrieve all employees");
			System.out.println("2. Create an employee");
			System.out.println("3. Update an employee using an ID");
			System.out.println("4. Delete an employee using an ID");
			System.out.println("5. Get the count of employees in the database");
			System.out.println("6. Close program");
			int side = scanner.nextInt();
			switch (side)
			{
				case 1:
					try
					{
						String urlString = "http://localhost:8080/api/v1/employees";
						String result = "";
						URL url = new URL(urlString);
						URLConnection conn = url.openConnection();
						
						BufferedReader rd = new BufferedReader (new InputStreamReader(conn.getInputStream()));
						String line;
						while ((line = rd.readLine()) != null) 
						{
							result += line;
						}
						rd.close();
						//Makes the JSON data more human readable.
						Gson gson = new GsonBuilder().setPrettyPrinting().create();
						@SuppressWarnings("deprecation")
						JsonParser jp = new JsonParser();
						@SuppressWarnings("deprecation")
						JsonElement je = jp.parse(result);
						String prettyJsonString = gson.toJson(je);
						System.out.println(prettyJsonString);
					
						} catch (IOException e) {
							System.out.println(e.getMessage());
						}
					
					break;
				case 2:
					System.out.println("What is the first name of the Employee?");
					String firstName = scanner.next();
					System.out.println("What is the last name of the Employee?");
					String lastName = scanner.next();
					System.out.println("What is the Employee's email address");
					String email = scanner.next();
					OkHttpClient client = new OkHttpClient().newBuilder()
							.build();
					MediaType mediaType = MediaType.parse("application/json");
					RequestBody body = RequestBody.create(mediaType, "{\r\n    \"firstName\": \""+ firstName + "\",\r\n    \"lastName\": \""+ lastName + "\",\r\n    \"emailId\": \""+ email + "\"\r\n}");
					Request request = new Request.Builder()
							.url("http://localhost:8080/api/v1/employee/create")
							.method("POST", body)
							.addHeader("Content-Type", "application/json")
							.build();
					Response response = client.newCall(request).execute();
					System.out.println(response.body().string());
					break;
				case 3:
					System.out.println("Which employee id would you like to change");
					int idChange = scanner.nextInt();
					System.out.println("What is the new first name of the Employee");
					String firstName1 = scanner.next();
					System.out.println("What is the new last name of the Employee");
					String lastName1 = scanner.next();
					System.out.println("What is the new last name of the Employee");
					String emailId = scanner.next();
					OkHttpClient client1 = new OkHttpClient().newBuilder()
					.build();
					MediaType mediaType1 = MediaType.parse("application/json");
					RequestBody body1 = RequestBody.create(mediaType1, "{\r\n    \"firstName\": \"" + firstName1 + "\",\r\n    \"lastName\": \"" + lastName1 + "\",\r\n    \"emailId\": \"" + emailId + "\"\r\n}");
					Request request1 = new Request.Builder()
							.url("http://localhost:8080/api/v1/employees/" + idChange)
							.method("PUT", body1)
							.addHeader("Content-Type", "application/json")
							.build();
					Response response1 = client1.newCall(request1).execute();
					System.out.println(response1.body().string());
				    

					break;
				case 4:	
					System.out.println("Please specifiy the id of the employee you want deleted");
					int idDelete = scanner.nextInt();
					try
					{
						String urlString = "http://localhost:8080/api/v1//employees/" + idDelete + "/delete";
						URL url = new URL(urlString);
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						conn.setRequestMethod("DELETE");
						String result = "";
						System.out.println();
						BufferedReader rd = new BufferedReader (new InputStreamReader(conn.getInputStream()));
						String line;
						while ((line = rd.readLine()) != null) 
						{
							result += line;
						}
						rd.close();
						System.out.println(result);
					} catch (IOException e) {
						System.out.println(e.getMessage());
					}
					break;
				case 5:
					try
					{
						String urlString = "http://localhost:8080/api/v1/employees/count";
						URL url = new URL(urlString);
						URLConnection conn = url.openConnection();
						String result = "";
						System.out.println();
						BufferedReader rd = new BufferedReader (new InputStreamReader(conn.getInputStream()));
						String line;
						while ((line = rd.readLine()) != null) 
						{
							result += line;
						}
						rd.close();
						System.out.println(result);
					} catch (IOException e) {
						System.out.println(e.getMessage());
					}
					break;
				case 6:
					finished = true;
					System.out.println("The program is closed.");
					break;
			}
		}
		
	}
}
