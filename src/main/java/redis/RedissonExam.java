package redis;

import java.beans.JavaBean;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import dao.CheckCardHistoryDao;
import dto.CheckCardRequestDto;
import dto.CheckCardResponseDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import service.CheckCardService;

public class RedissonExam {
	public static CheckCardHistoryDao dao = new CheckCardHistoryDao();

	 	public static final CheckCardService service = new CheckCardService(dao);

	   public static CheckCardResponseDto cardLock(CheckCardRequestDto dto)
	   {
		   CheckCardResponseDto response=null;
		   RedissonClient redisson = null;
	        Config config = new Config();
	        config.useSingleServer().setAddress("redis://localhost:6379");
	        redisson = Redisson.create(config);
		   //어떤걸 기준으로 락을 잡을것인가
//	        redisson.getBucket("franchisee").set(String.format("MegaCoffee"));
//	        System.out.println("레디스에 저장된 프랜차이즈는 :"+redisson.getBucket("franchisee").get());		      
	        
	        
	        RLock lock = redisson.getLock(String.format("cardId:%d",dto.getCardId()));

	        //락 시작
	        try {
	            boolean available = lock.tryLock(10, 2, TimeUnit.SECONDS);
	            if (!available) { //락을 점유하지 못했다면
	                System.out.println("redisson getlock timeout");
	                throw new IllegalArgumentException();
	            }
	            System.out.println("락아이디:"+lock.getName());
	            try {
	            	response=service.checkCardPayment(dto);
				} catch (Exception e) {
					e.printStackTrace();
				}
	            //그렇지 않다면 락 점유 성공->본격적으로 주문 시작
	        } catch (InterruptedException e) {
	            throw new RuntimeException(e);
	        } finally {
	        	if(Objects.nonNull(lock)&&lock.isLocked()&&lock.isHeldByCurrentThread()) {
		            lock.unlock();
		            System.out.println("락 해세");
	        	}
	        }
			return response;
	    }
	   }



	
//	public static void main(String[]args)
//	{
//		cardLock();
//	}


