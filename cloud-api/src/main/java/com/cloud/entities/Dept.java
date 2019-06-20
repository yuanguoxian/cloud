package com.cloud.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Dept {

	private Long deptno;
	private String dname;
	private String db_source;
}
