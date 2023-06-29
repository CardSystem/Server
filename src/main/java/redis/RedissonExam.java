package redis;

import java.beans.JavaBean;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import dao.AccountDao;
import dao.CardDao;
import dao.CardHistoryDao;
import dto.CheckCardRequestDto;
import dto.CheckCardResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import service.CheckCardService;

@Setter
public class RedissonExam {
		public static CardHistoryDao historydao = new CardHistoryDao();
		public static CardDao carddao = new CardDao();
		public static AccountDao accountdao = new AccountDao();

	 	public static CheckCardService service = new CheckCardService(historydao,accountdao,carddao);

	 	public static CheckCardResponseDto cardLock(CheckCardRequestDto dto) throws Exception{
		   CheckCardResponseDto response=null;
	        Config config = new Config();
	        config.useSingleServer().setAddress("redis://localhost:6379");
	        RedissonClient redisson = Redisson.create(config);
		   //어떤걸 기준으로 락을 잡을것인가


	        redisson.getLock(String.valueOf(dto.getCardId()));
	        RLock lock = redisson.getLock(String.valueOf(dto.getCardId()));
	        try {
	            boolean available = lock.tryLock(10, 2, TimeUnit.SECONDS);
	            if (!available) { //락을 점유하지 못했다면
	                System.out.println("redisson getlock timeout");
	                throw new IllegalArgumentException();
	            }
	            System.out.println("점유한 스레드:"+lock.getName());
	            return service.checkCardPayment(dto);
	        } catch (InterruptedException e) {
	            throw new RuntimeException(e);
	        } finally {
	        	  if(lock != null && lock.isLocked()) {
	            lock.unlock();
	            System.out.println("락 해세");
	        	  }

	        }

	    }

		public void setService(CheckCardService checkCardService) {
			// TODO Auto-generated method stub
			this.service=checkCardService;
		}
	   }


