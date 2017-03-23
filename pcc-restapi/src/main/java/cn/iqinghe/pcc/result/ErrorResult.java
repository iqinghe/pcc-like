package cn.iqinghe.pcc.result;

/**
 * 调用错误返回的json对象
 * 
 * @author chengang
 *
 */
public class ErrorResult {

	// 错误编码
	private int errorCode;
	// 错误信息
	private String errorMessage;

	public ErrorResult() {

	}

	public ErrorResult(int errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
