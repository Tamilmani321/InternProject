package com.user.auditables;
import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable {

	@CreatedBy
	protected int createdBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	protected Date createdDate;
	
	@LastModifiedBy
	protected int lastModifiedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	protected Date lastModifiedDate;

}
