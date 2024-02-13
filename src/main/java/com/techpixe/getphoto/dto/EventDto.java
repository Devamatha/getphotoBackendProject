package com.techpixe.getphoto.dto;

public class EventDto {

	private Long event_Id;

	private String eventName;

	private String eventAddress;

	private byte[] qrCode;

	public Long getEvent_Id() {
		return event_Id;
	}

	public void setEvent_Id(Long event_Id) {
		this.event_Id = event_Id;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventAddress() {
		return eventAddress;
	}

	public void setEventAddress(String eventAddress) {
		this.eventAddress = eventAddress;
	}

	public byte[] getQrCode() {
		return qrCode;
	}

	public void setQrCode(byte[] qrCode) {
		this.qrCode = qrCode;
	}

}
