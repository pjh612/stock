package com.example.stock.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockService {

	private final StockRepository stockRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void decrease(Long id, Long quantity) {
		Stock stock = stockRepository.findById(id).orElseThrow();

		stock.decrease(quantity);

		stockRepository.saveAndFlush(stock);
	}

	public synchronized void synchronizedDecrease(Long id, Long quantity) {
		Stock stock = stockRepository.findById(id).orElseThrow();

		stock.decrease(quantity);

		stockRepository.saveAndFlush(stock);
	}

	@Transactional
	public void pessimisticLockDecrease(Long id, Long quantity) {
		Stock stock = stockRepository.findByIdWithPessimisticLock(id).orElseThrow();

		stock.decrease(quantity);

		stockRepository.saveAndFlush(stock);
	}

	@Transactional
	public void optimisticLockDecrease(Long id, Long quantity) {
		Stock stock = stockRepository.findByIdWithOptimisticLock(id).orElseThrow();

		stock.decrease(quantity);

		stockRepository.saveAndFlush(stock);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void namedLockDecrease(Long id, Long quantity) {
		Stock stock = stockRepository.findById(id).orElseThrow();

		stock.decrease(quantity);

		stockRepository.saveAndFlush(stock);
	}
}
