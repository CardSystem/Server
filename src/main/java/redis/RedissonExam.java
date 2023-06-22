package redis;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import dto.CheckCardRequestDto;
import lombok.AllArgsConstructor;
import service.CheckCardService;

@AllArgsConstructor
public class RedissonExam {
	private final RedissonClient redissonClient;
	private final CheckCardService checkCardService;

	public RedissonExam(CheckCardService checkCardService, RedissonClient redissonClient) {
		this.redissonClient = redissonClient;
		this.checkCardService = checkCardService;

	}

	public void getLock(CheckCardRequestDto dto) {

		RLock lock = redissonClient.getLock(String.format("order:user:%d", dto.getAccountId()));

		// 락 시작
		try {
			boolean available = lock.tryLock(10, 2, TimeUnit.SECONDS);
			if (!available) { // 락을 점유하지 못했다면
				System.out.println("redisson getlock timeout");
				throw new IllegalArgumentException();
			}
			System.out.println(lock.getName());
			return orderService.getOrder_ByDBLock_Failure(orderRequestDto);
			// 그렇지 않다면 락 점유 성공->본격적으로 주문 시작
//	            return orderService.getOrder_ByRedis(orderRequestDto); //따로 db에 락을 걸지 않아도 실행 됨
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			lock.unlock();
		}
	}

}
