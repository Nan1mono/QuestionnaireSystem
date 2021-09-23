package pres.ljc.project.util;

import java.util.List;

public class StResult{
	private int code; // 状态码
	private String msg; // 信息
	private List data;// 集合数据对象
	private Object obj;// 单个对象
	public StResult(){}
	public StResult(int code){
		this.code = code;
	}
	public StResult(int code,String msg){
		this.code = code;
		this.msg = msg;
	}
	public StResult(int code,String msg,List data){
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	public StResult(int code,String msg,List data,Object obj){
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.obj = obj;
	}
	public static StResult ok(){
		StResult instance = new StResult();
		instance.setCode(Data.RESPONSE_OK);
		return instance;
	}
	public static StResult ok(String msg){
		StResult instance = new StResult();
		instance.setCode(Data.RESPONSE_OK);
		instance.setMsg(msg);
		return instance;
	}
	public static StResult ok(List data){
		StResult instance = new StResult();
		instance.setCode(Data.RESPONSE_OK);
		instance.setData(data);
		return instance;
	}
	public static StResult ok(String msg,List data){
		StResult instance = new StResult();
		instance.setCode(Data.RESPONSE_OK);
		instance.setData(data);
		instance.setMsg(msg);
		return instance;
	}
	public static StResult ok(Object obj){
		StResult instance = new StResult();
		instance.setCode(Data.RESPONSE_OK);
		instance.setObj(obj);
		return instance;
	}
	public static StResult ok(String msg,Object obj){
		StResult instance = new StResult();
		instance.setCode(Data.RESPONSE_OK);
		instance.setMsg(msg);
		instance.setObj(obj);
		return instance;
	}
	public static StResult ok(String msg,Object obj,List data){
		StResult instance = new StResult();
		instance.setCode(Data.RESPONSE_OK);
		instance.setMsg(msg);
		instance.setObj(obj);
		instance.setData(data);
		return instance;
	}

	public static StResult fail(){
		StResult instance = new StResult();
		instance.setCode(Data.RESPONSE_FAIL);
		return instance;
	}
	public static StResult fail(String msg){
		StResult instance = new StResult();
		instance.setCode(Data.RESPONSE_FAIL);
		instance.setMsg(msg);
		return instance;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
}