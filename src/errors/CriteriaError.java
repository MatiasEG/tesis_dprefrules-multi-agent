package errors;

public class CriteriaError {

	private String word;
	private String error;
	private String msg;
	
	public CriteriaError(String word, String error, String msg) {
		this.word = word;
		this.error = error;
		this.msg = msg;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
