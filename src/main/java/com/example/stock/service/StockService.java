package com.example.stock.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockService {

	private final StockRepository stockRepository;

	@Transactional
	public void decrease(Long id, Long quantity) {
		Stock stock = stockRepository.findById(id).orElseThrow();

		stock.decrease(quantity);

		stockRepository.saveAndFlush(stock);
	}
}
