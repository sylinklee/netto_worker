package com.netto.schedule.demo.mock;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class OuterMockService {
	public ServiceResponse<String> syncMessge(String body) {
		ServiceResponse<String> response = new ServiceResponse<String>();
		int t = new Random(System.currentTimeMillis()).nextInt(100);
		if (t % 10 == 0) {
			throw new RuntimeException("error %10 ==0");
		} else if (t % 3 == 0) {
			response.setSuccess(false);
			response.setErrorMsg("error %3==0");
			return response;
		} else {
			response.setSuccess(true);
			response.setBody("t=" + t);
			return response;
		}

	}

	public class ServiceResponse<T> {
		private Boolean success;
		private String errorMsg;
		private T body;

		public Boolean getSuccess() {
			return success;
		}

		public void setSuccess(Boolean success) {
			this.success = success;
		}

		public String getErrorMsg() {
			return errorMsg;
		}

		public void setErrorMsg(String errorMsg) {
			this.errorMsg = errorMsg;
		}

		public T getBody() {
			return body;
		}

		public void setBody(T body) {
			this.body = body;
		}
	}
}
