package models;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Reimbursement {
	public int id;
	public String employee;
	public String description;
	private byte[] imgData = null;
	private String imgUrl = null;
	public ReimbursementStatus status = ReimbursementStatus.Pending;
	public String manager = null;

	public Reimbursement(String _employee, String _description) {
		employee = _employee;
		description = _description;
	}

	public byte[] getImgData() {
		return imgData;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImageFromUrl(String _url) {
		imgUrl = _url;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(ImageIO.read(new File(imgUrl)), "jpg", out);
			imgData = out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setImageFromRawData(byte[] _data, String _url) {
		imgUrl = _url;
		imgData = _data;
		try {
			if (imgData != null) {
				ImageIO.write(ImageIO.read(new ByteArrayInputStream(_data)), "jpg",
						new FileOutputStream(imgUrl, false));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return id + "," + employee + "," + description + "," + imgUrl + "," + status + "," + manager;
	}
}
