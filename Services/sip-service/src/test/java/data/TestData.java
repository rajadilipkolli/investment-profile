package data;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.zakura.sipservice.models.SipVO;

public class TestData {

	private static ObjectMapper mapper = new ObjectMapper();
	private static final String SIPVO_REQUEST = "./src/test/resources/SipVo.json";

	public static SipVO getSipVo() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Gson gson = new Gson();
		return gson.fromJson(new FileReader(SIPVO_REQUEST), SipVO.class);
	}

	public static String getSipVoString()
			throws JsonSyntaxException, JsonIOException, JsonProcessingException, FileNotFoundException {
		return mapper.writeValueAsString(getSipVo());
	}

}
