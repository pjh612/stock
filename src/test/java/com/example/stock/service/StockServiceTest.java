package com.example.stock.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;

@SpringBootTest
class StockServiceTest {

	@Autowired
	StockService stockService;

	@Autowired
	StockRepository stockRepository;

	@BeforeEach
	public void before() {
		Stock stock = new Stock(1L, 100L);

		stockRepository.save(stock);
	}

	@AfterEach
	public void after() {
		stockRepository.deleteAll();
	}

	@Test
	void stockDecrease() {
		stockService.decrease(1L, 1L);

		Stock foundStock = stockRepository.findById(1L).orElseThrow();

		assertThat(foundStock.getQuantity()).isEqualTo(99L);
	}

	@Test
	void stockDecreaseMultiThread() throws InterruptedException {
		int threadCount = 100;
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch latch = new CountDownLatch(threadCount);
		for (int i = 0; i < threadCount; i++) {

			executorService.submit(() -> {
				try {
					stockService.decrease(1L, 1L);
				} finally {
					latch.countDown();
				}
			});

		}

		latch.await();

		Stock foundStock = stockRepository.findById(1L).orElseThrow();

		assertThat(foundStock.getQuantity()).isNotEqualTo(0);
	}

	@Test
	void stockSynchronizedDecreaseMultiThread() throws InterruptedException {
		int threadCount = 100;
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch latch = new CountDownLatch(threadCount);
		for (int i = 0; i < threadCount; i++) {

			executorService.submit(() -> {
				try {
					stockService.synchronizedDecrease(1L, 1L);
				} finally {
					latch.countDown();
				}
			});

		}

		latch.await();

		Stock foundStock = stockRepository.findById(1L).orElseThrow();

		assertThat(foundStock.getQuantity()).isEqualTo(0);
	}

}