package com.cos.bus.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusInfo {
	private String arrPlaceNm;
	private String charge;
	private String depPlaceNm;
	private String gradeNm;
	private String depPlandTime;
	private String arrPlandTime;
}
