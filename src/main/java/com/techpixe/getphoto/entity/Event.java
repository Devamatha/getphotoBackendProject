package com.techpixe.getphoto.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long event_Id;

	@Column(name = "event_Name", nullable = false)
	private String eventName;

	@Column(name = "event_Address", nullable = false)
	private String eventAddress;

	private Date eventDate;
	@Lob
	@Column(columnDefinition = "longblob", name = "qrCode", nullable = false)
	private byte[] qrCode;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "photographer_Id")
	private PhotoGrapher photoGrapher;

}
