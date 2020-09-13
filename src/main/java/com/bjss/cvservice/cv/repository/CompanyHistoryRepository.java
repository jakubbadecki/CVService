package com.bjss.cvservice.cv.repository;

import com.bjss.cvservice.cv.entity.dao.CompanyHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyHistoryRepository extends MongoRepository<CompanyHistory, String> {
}
