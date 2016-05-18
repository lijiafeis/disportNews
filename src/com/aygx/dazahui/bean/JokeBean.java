package com.aygx.dazahui.bean;

import java.util.List;
/**
 * 用来封装从网络中获取的joke数据
 * @author Administrator
 *
 */
public class JokeBean {
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
			
		}
	}
}
