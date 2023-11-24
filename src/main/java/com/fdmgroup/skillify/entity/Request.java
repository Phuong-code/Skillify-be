package com.fdmgroup.skillify.entity;

import com.fdmgroup.skillify.enums.RequestType;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "request")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private AppUser sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private AppUser receiver;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type", nullable = false)
    private RequestType type;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false)
    private Timestamp createdDate;

    @Column(name = "expired_date")
    private Timestamp expiredDate;
    
    @Column(name = "content", nullable = true)
    private String content;
    

	public Request() {
		super();

	}

	public Request(AppUser sender, AppUser receiver, RequestType type, Timestamp createdDate, Timestamp expiredDate,
			String content) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.type = type;
		this.createdDate = createdDate;
		this.expiredDate = expiredDate;
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public AppUser getSender() {
		return sender;
	}

	public void setSender(AppUser sender) {
		this.sender = sender;
	}

	public AppUser getReceiver() {
		return receiver;
	}

	public void setReceiver(AppUser receiver) {
		this.receiver = receiver;
	}

	public RequestType getType() {
		return type;
	}

	public void setType(RequestType type) {
		this.type = type;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Timestamp expiredDate) {
		this.expiredDate = expiredDate;
	}

	@Override
	public String toString() {
		return "Request [id=" + id + ", sender=" + sender + ", receiver=" + receiver + ", type=" + type
				+ ", createdDate=" + createdDate + ", expiredDate=" + expiredDate + "]";
	}
    
    
}
