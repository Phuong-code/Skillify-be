package com.fdmgroup.skillify.dto.Request;



import java.util.UUID;

import com.fdmgroup.skillify.enums.RequestType;

public class RequestPublicDto {

	private UUID requestId;
    private String senderEmail;
    private String receiverEmail;
    private RequestType type;
    private String content;
    private String expiredDate;
    private String createdDate;
    private String senderName;
    private String receiverName;
	public RequestPublicDto() {
		super();

	}

	public RequestPublicDto(UUID requestId, String senderEmail, String receiverEmail, RequestType type,
			String content, String expiredDate) {
		super();
		this.requestId = requestId;
		this.senderEmail = senderEmail;
		this.receiverEmail = receiverEmail;
		this.type = type;
		this.content = content;
		this.expiredDate = expiredDate;
	}

	
	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public UUID getRequestId() {
		return requestId;
	}

	public void setRequestId(UUID requestId) {
		this.requestId = requestId;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}

	
}
