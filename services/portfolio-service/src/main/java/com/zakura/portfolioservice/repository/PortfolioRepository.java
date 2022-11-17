package com.zakura.portfolioservice.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.zakura.portfolioservice.aspect.LogProcessTime;
import com.zakura.portfolioservice.models.Investment;

public interface PortfolioRepository extends MongoRepository<Investment, String> {
	
	@LogProcessTime
	Optional<ArrayList<Investment>> findByUserName(String userName);

	@LogProcessTime
	Optional<Investment> findByUserNameAndName(String userName, String name);

	@LogProcessTime
	Optional<Investment> findByUserNameAndNameAndType(String userName, String name, String type);

	@LogProcessTime
	int deleteByUserNameAndNameAndType(String userId, String name, String type);

}