package com.zn.core.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.io.Serializable;

@Data
@JsonIgnoreProperties(value = {"handler"})//报错
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = -8951883345126705914L;
	/**
	 * 主键id
	 */
	@TableId(value = "id_", type = IdType.AUTO)
	protected Long id;

}
