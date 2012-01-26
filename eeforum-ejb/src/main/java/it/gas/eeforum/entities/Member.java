package it.gas.eeforum.entities;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "members")
@NamedQueries({
		@NamedQuery(name = "member.login", query = "SELECT m FROM Member m WHERE m.email = :user AND m.password = :pass"),
		@NamedQuery(name = "member.nick", query = "SELECT m FROM Member m WHERE m.nickname = :nick"),
		@NamedQuery(name = "member.mail", query = "SELECT m FROM Member m WHERE m.email = :mail") })
public class Member implements Serializable {
	private static final long serialVersionUID = 1L;

	private String email;
	private String emailHash;
	private String password;
	private String nickname;
	private boolean admin;
	private boolean banned;

	@PrePersist
	public void onPersist() {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			String str = email.trim().toLowerCase();
			md.update(str.getBytes(Charset.forName("UTF-8")));
			byte[] b = md.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < b.length; i++) {
				sb.append(Integer.toHexString((b[i] & 0xFF) | 0x100).substring(
						1, 3));
			}
			emailHash = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			emailHash = "00000000000000000000000000000000";
		}
	}

	@Id
	@Column(length = 255, nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(length = 32, nullable = false)
	public String getEmailHash() {
		return emailHash;
	}
	
	public void setEmailHash(String emailHash) {
		this.emailHash = emailHash;
	}

	@Column(length = 40, nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(length = 25, nullable = false, unique = true)
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isBanned() {
		return banned;
	}

	public void setBanned(boolean banned) {
		this.banned = banned;
	}

}
