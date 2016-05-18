package com.aygx.dazahui.bean;

import java.util.List;

import com.aygx.dazahui.bean.JokeBean.Shuju;
import com.aygx.dazahui.bean.JokeBean.Shuju.Data;


/**
 * 用来封装从网上的到数图片的信息。
 * @author Administrator
 *
 */
public class JokePicBean {

	private int error_code;
	private String reason;
	private Shuju result;
	
	
	public int getError_code() {
		return error_code;
	}


	public void setError_code(int error_code) {
		this.error_code = error_code;
	}


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	public Shuju getResult() {
		return result;
	}


	public void setResult(Shuju result) {
		this.result = result;
	}


	public class Shuju{
		private List<Data> data;
		
		public List<Data> getData() {
			return data;
		}

		public void setData(List<Data> data) {
			this.data = data;
		}

		public class Data{
			private String content;
			private String hashId;
			private String unixtime;
			private String updatetime;
			private String url;
			public String getContent() {
				return content;
			}
			public void setContent(String content) {
				this.content = content;
			}
			public String getHashId() {
				return hashId;
			}
			public void setHashId(String hashId) {
				this.hashId = hashId;
			}
			public String getUnixtime() {
				return unixtime;
			}
			public void setUnixtime(String unixtime) {
				this.unixtime = unixtime;
			}
			public String getUpdatetime() {
				return updatetime;
			}
			public void setUpdatetime(String updatetime) {
				this.updatetime = updatetime;
			}
			public String getUrl() {
				return url;
			}
			public void setUrl(String url) {
				this.url = url;
			}
			
		}
	}


}
	
