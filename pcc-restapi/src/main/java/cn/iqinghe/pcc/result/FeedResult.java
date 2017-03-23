package cn.iqinghe.pcc.result;

import java.util.LinkedList;
import java.util.List;

import cn.iqinghe.pcc.domain.Feed;

public class FeedResult {
	// 成功:0，其他编码为不成功
	private int code = -1;
	private List<Feed> result = new LinkedList<Feed>();

	public FeedResult() {
		super();
	}

	public FeedResult(int code, List<Feed> result) {
		super();
		this.code = code;
		this.result = result;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public List<Feed> getResult() {
		return result;
	}

	public void setResult(List<Feed> result) {
		this.result = result;
	}

}
