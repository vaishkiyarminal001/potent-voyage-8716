package com.covisafe.service;

import java.util.List;

import com.covisafe.modal.IdCard;


public interface IdCardService {

	public List<IdCard> getAllIdCard();
	public IdCard getIdCardById(String id);
	public IdCard getIdCardByAadharNo(String aadharNo);
	public IdCard getIdCardByPanNo(String panNo);
	public IdCard addIdCard(IdCard member);
	public IdCard updateIdCard(String IdCardId , IdCard member);
	public Boolean deleteIdCard(String id);
	
}
