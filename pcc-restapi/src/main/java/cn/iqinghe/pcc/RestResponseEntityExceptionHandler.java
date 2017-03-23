package cn.iqinghe.pcc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cn.iqinghe.pcc.result.ErrorResult;

@ControllerAdvice(annotations = RestController.class)
public class RestResponseEntityExceptionHandler {
	private final static Logger logger = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ErrorResult handleResourceNotFound(NullPointerException ex) {
		logger.error(ex.getMessage(), ex);
		return new ErrorResult(4000, "系统错误");
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	protected ErrorResult handleException(Exception ex) {
		logger.error(ex.getMessage(), ex);
		return new ErrorResult(1000, "系统错误");
	}

	@ExceptionHandler(Error.class)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	protected ErrorResult handleError(Error ex) {
		logger.error(ex.getMessage(), ex);
		return new ErrorResult(2000, "系统错误");
	}

}