package com.backend.models;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name="INVENTAIRE")
public @Data class Inventaire {

		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		@Column(name="ID_INVTR")
		Long id;
		@Column(name="DATE_INVTR")
		LocalDateTime date;
		@Column(name="DESCRIPTION_INVTR")
		String description;
		@JoinColumn(name="STOCK_INVTR")
		@ManyToOne(targetEntity=Stock.class, fetch=FetchType.EAGER)
		Stock stock;

}
