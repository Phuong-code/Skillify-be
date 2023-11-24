package com.fdmgroup.skillify.dto.Request;



import java.util.UUID;

import com.fdmgroup.skillify.enums.RequestType;

public class RequestDto {

    private String senderEmail;
    private String receiverEmail;
    private RequestType type;
    private String content;
    private String expiredDate;
    
	public RequestDto() {
		super();

	}

	public RequestDto(String senderEmail, String receiverEmail, RequestType type, String content, String expiredDate) {
		super();
		this.senderEmail = senderEmail;
		this.receiverEmail = receiverEmail;
		this.type = type;
		this.content = content;
		this.expiredDate = expiredDate;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}


	public String getReceiverEmail() {
		return receiverEmail;
	}


	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}

	public RequestType getType() {
		return type;
	}

	public void setType(RequestType type) {
		this.type = type;
	}
	
	public String getExpiredDate() {
		return expiredDate;
	}


	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}
	
	






    
}
