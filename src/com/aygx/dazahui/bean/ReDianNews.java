package com.aygx.dazahui.bean;

import java.util.List;
/**
 * 	名称	类型	说明
 	error_code	int	返回码
 	reason	string	返回说明
 	result	string	返回结果集
 	title	string	新闻标题
 	content	string	新闻摘要内容
 	img_width	string	图片宽度
 	full_title	string	完整标题
 	pdate	string	发布时间
 	src	string	新闻来源
 	img_length	string	图片高度
 	img	string	图片链接
 	url	string	新闻链接
 	pdate_src	string	发布完整时间
 * @author Administrator
 *
 */
public class ReDianNews {

	private String reason;
	private List<Result> result;
	private int error_code;
	
	public static class Result{
		private String title;
		private String content;
		private String img_width;
		private String full_title;
		private String pdate;
		private String src;
		private String img_length;
		private String img;
		private String url;
		private String pdate_src;
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getImg_width() {
			return img_width;
		}
		public void setImg_width(String img_width) {
			this.img_width = img_width;
		}
		public String getFull_title() {
			return full_title;
		}
		public void setFull_title(String full_title) {
			this.full_title = full_title;
		}
		public String getPdate() {
			return pdate;
		}
		public void setPdate(String pdate) {
			this.pdate = pdate;
		}
		public String getSrc() {
			return src;
		}
		public void setSrc(String src) {
			this.src = src;
		}
		public String getImg_length() {
			return img_length;
		}
		public void setImg_length(String img_length) {
			this.img_length = img_length;
		}
		public String getImg() {
			return img;
		}
		public void setImg(String img) {
			this.img = img;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getPdate_src() {
			return pdate_src;
		}
		public void setPdate_src(String pdate_src) {
			this.pdate_src = pdate_src;
		}
		@Override
		public String toString() {
			return "Result [title=" + title + ", content=" + content
					+ ", img_width=" + img_width + ", full_title=" + full_title
					+ ", pdate=" + pdate + ", src=" + src + ", img_length="
					+ img_length + ", img=" + img + ", url=" + url
					+ ", pdate_src=" + pdate_src + "]";
		}
		
		
	}
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public List<Result> getResult() {
		return result;
	}
	public void setResult(List<Result> result) {
		this.result = result;
	}
	public int getError_code() {
		return error_code;
	}
	public void setError_code(int error_code) {
		this.error_code = error_code;
	}
	@Override
	public String toString() {
		return "ReDianNews [reason=" + reason + ", result=" + result
				+ ", error_code=" + error_code + "]";
	}
	
}
