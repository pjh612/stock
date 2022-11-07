package com.example.stock.facade;

import org.springframework.stereotype.Service;

import com.example.stock.service.StockService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OptimisticLockStockFacade {

	private final StockService stockService;

	public void decrease(Long id, Long quantity) throws InterruptedException {
		while (true) {
			try {
				stockService.optimisticLockDecrease(id, quantity);

				break;
			} catch (Exception e) {
				Thread.sleep(50);
			}
		}
	}
}
