package service;

import java.time.LocalDateTime;

import exception.*;
import dao.CheckCardHistoryDao;
import domain.Account;
import domain.Cards;
import domain.CheckCardHistory;
import dto.AccountDto;
import dto.CheckCardDaoToServiceDto;
import dto.CheckCardHistoryDto;
import dto.CheckCardRequestDto;
import exception.BusinessException;
import exception.ErrorCode.*;


public class CheckCardService {
	
	public final CheckCardHistoryDao checkcarddao = new CheckCardHistoryDao();
	public Boolean isStoppedCard(Long cardId)
	{
		CheckCardDaoToServiceDto card=checkcarddao.selectCardByCardId(cardId);
		if(card.getIsStopped()==1)
			return false;
		
		else
			return true;
	}
	
	
	public Boolean isStoppedAccount(Long cardId)
	{
		AccountDto account=checkcarddao.selectAccountByCardId(cardId);

		if(account.getIsStopped()==1)
			return false;
		else
			return true;
	}
	

	
	public void checkCardPayment(CheckCardRequestDto dto) throws Exception
	{
	    CheckCardHistoryDto historydto = null;
		Long cardId=dto.getCardId();

		//dao에서 받아올 때 dto로 받아오기
		AccountDto accountdto=checkcarddao.selectAccountByCardId(cardId);
		CheckCardDaoToServiceDto carddto=checkcarddao.selectCardByCardId(cardId);


		try {
			Long payment=dto.getPayment();
			if(!isStoppedCard(cardId))
			{
				throw new BusinessException(ErrorCode.STOPPED_CARD,"정지된 카드입니다");
			}
			if(!isStoppedAccount(cardId))
			{
				throw new BusinessException(ErrorCode.STOPPED_ACCOUNT,"정지된 계좌입니다");
			}
			if(accountdto.getBalance()<payment)
			{
				throw new BusinessException(ErrorCode.INSUFFICIENT_BALANCE,"잔액이 부족합니다.");
			}

			Double paymentReal=dto.getPayment()*(-1)*((100-carddto.getDiscount())*0.01);

			accountdto.makeBalance((new Double(paymentReal)).longValue());
			
			checkcarddao.updateBalance(accountdto.getId(), accountdto.getBalance());
			System.out.println("결재후 잔액은: "+accountdto.getBalance());
			historydto=new CheckCardHistoryDto(dto.getCardId(),dto.getUserId(),dto.getFranchisee(),
					dto.getPayment(),accountdto.getBalance(),
					1,dto.getDate(),dto.getFCategory(),0,0,carddto.getCardType());
			checkcarddao.insertData(historydto);

		}
		catch(BusinessException e)
		{
			 System.out.println("에러발생: " + e.getMessage());
			 historydto = new CheckCardHistoryDto(dto.getCardId(), 
					 dto.getUserId(), 
					 dto.getFranchisee(),
		             dto.getPayment(),accountdto.getBalance() , 0, 
		             dto.getDate(), dto.getFCategory(), 1, 0, carddto.getCardType());
		        checkcarddao.insertData(historydto);
		}

	}

		
		
}
	
	
	
	