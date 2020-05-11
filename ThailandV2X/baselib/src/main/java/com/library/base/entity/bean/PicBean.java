package com.library.base.entity.bean;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PicBean implements Serializable {
	/**
	 * id : 21
	 * name : 4751adcd31bafb28f030c597131916bb.jpeg
	 * size : 25711
	 * path : https://claim-sp.oss-cn-hangzhou.aliyuncs.com/20180108/4751adcd31bafb28f030c597131916bb.jpeg
	 * thumb : https://claim-sp.oss-cn-hangzhou.aliyuncs.com/20180108/4751adcd31bafb28f030c597131916bb.jpeg?x-oss-process=style/thumb
	 */
	/**
	 * id : 1404
	 * path : https://claim-test.oss-cn-hangzhou.aliyuncs.com/20180119/2ee12297fea469405cb09577fb2ea4b0.jpeg
	 * thumb : https://claim-test.oss-cn-hangzhou.aliyuncs.com/20180119/2ee12297fea469405cb09577fb2ea4b0.jpeg?x-oss-process=style/thumb
	 * name : 2018年01月19日 14:32:570
	 * size : 53790
	 * type : oss
	 */

	@SerializedName("id")
	private String id;
	@SerializedName("name")
	private String name;
	@SerializedName("size")
	private int size;
	@SerializedName("path")
	private String path;
	@SerializedName("thumb")
	private String thumb;

	@SerializedName("type")
	private String type;//后台type

	private String typeLoc;//本地type
	private Bitmap bitmap;

	private List<RePicBean> rePicBean ;

	public List<RePicBean> getRePicBean() {
		return rePicBean;
	}

	public void setRePicBean(List<RePicBean> rePicBean) {
		this.rePicBean = rePicBean;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeLoc() {
		return typeLoc;
	}

	public void setTypeLoc(String typeLoc) {
		this.typeLoc = typeLoc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}


	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public static class RePicBean implements Serializable{
		@SerializedName("id")
		private String id;
		@SerializedName("name")
		private String name;
		@SerializedName("size")
		private int size;
		@SerializedName("path")
		private String path;
		@SerializedName("thumb")
		private String thumb;

		@SerializedName("type")
		private String type;//后台type

		private String typeLoc;//本地type
		private Bitmap bitmap;


		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getSize() {
			return size;
		}

		public void setSize(int size) {
			this.size = size;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public String getThumb() {
			return thumb;
		}

		public void setThumb(String thumb) {
			this.thumb = thumb;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getTypeLoc() {
			return typeLoc;
		}

		public void setTypeLoc(String typeLoc) {
			this.typeLoc = typeLoc;
		}

		public Bitmap getBitmap() {
			return bitmap;
		}

		public void setBitmap(Bitmap bitmap) {
			this.bitmap = bitmap;
		}
	}
}
