package com.sonarUser;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class SonarqubeNewUser2 {
	ClientConfig clientConfig = new DefaultClientConfig();
	Client client = Client.create(clientConfig);
	WebResource webResource;
	MultivaluedMap<String, String> formData;
	ClientResponse response;
	
	public static void main(String[] args) {
		SonarqubeNewUser2 sonarqubeNewUser = new SonarqubeNewUser2();
		sonarqubeNewUser.loginUser();
		sonarqubeNewUser.CreateNewUser();
	}
	public void loginUser() {
		client.addFilter(new LoggingFilter());
		webResource = client.resource("http://localhost:9000/api/authentication/login");
		formData = new MultivaluedMapImpl();
		formData.add("login", "admin");
		formData.add("password", "admin");
		response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class,formData);
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failure occured : "+response.getStatus());
		}else {
			System.out.println("Success "+ response.getStatus());
		}
	}
	public void CreateNewUser() {	
		webResource = client.resource("http://localhost:9000/api/users/create");
		formData = new MultivaluedMapImpl();
		formData.add("login", "Kumar121");
		formData.add("name", "KumarShanu");
		formData.add("email", "kumar@Qmetry.com");
		formData.add("password", "kumar21");
		
		Builder builder = webResource.header("X-XSRF-TOKEN", response.getCookies().get(1).getValue());
		for( int i=0; i<response.getCookies().size(); i++) {
			builder = builder.cookie(response.getCookies().get(i));
		}
		response = builder.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class,formData);
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failure occured : "+response.getStatus());
		}else {
			System.out.println("Success "+ response.getStatus());
		}
	  }	
	}

//Request URL:http://localhost:9000/api/authentication/login
//login=admin&password=admin
//X-XSRF-TOKEN:lot5gfnvtavatgq55r5crrich7
//Cookie:XSRF-TOKEN=lot5gfnvtavatgq55r5crrich7;
//JWT-SESSION=eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJBV0ZMaG5DVjhNOFliVlFSWGhUQyIsInN1YiI6ImFkbWluIiwiaWF0IjoxNTE3MzkwNTU3LCJleHAiOjE1MTc2NTUwMzIsImxhc3RSZWZyZXNoVGltZSI6MTUxNzM5MDU1NzMzMywieHNyZlRva2VuIjoibG90NWdmbnZ0YXZhdGdxNTVyNWNycmljaDcifQ.oYiD9zIfphV7Ua-2_IDk4roYeJia8C3OF08KNtcAL5g
//Request URL:http://localhost:9000/api/users/create
//login=abcd&name=abcd&email=abcd%40abcd.com&password=abcd&scmAccount=
